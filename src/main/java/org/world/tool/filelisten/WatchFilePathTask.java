package org.world.tool.filelisten;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.world.util.file.YamlUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

/**
 * 监听 “mashanggou.yml”文件变化，动态加载
 *
 * @author cancer
 */
public class WatchFilePathTask extends Thread {
	private Log log = LogFactory.getLog(WatchFilePathTask.class);

	private String linstenPath = System.getProperty("user.dir");
	private String filePath = System.getProperty("user.dir") + File.separator + "mashanggou.yml";

	private WatchService watchService;

	@Getter
	private List<String> data;

	public WatchFilePathTask() throws URISyntaxException, IOException {
		String sourcePath = WatchFilePathTask.class.getResource("/").getPath();
		//码上购初始化
		File file = new File(sourcePath + "mashanggou.yml");
		File distFile = new File(filePath);
		if (!distFile.exists()) {
			distFile.createNewFile();
		}
		FileUtils.copyFile(file, distFile);
	}

	@Override
	public void run() {
		try {
			//获取监控服务
			watchService = FileSystems.getDefault().newWatchService();
			log.debug("获取监控服务" + watchService);
			Path path = Paths.get(linstenPath);
			log.debug("码上购文件监听路径：" + linstenPath+";监听文件路径："+filePath);

			//注册监控服务，监控新增事件
			WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				for (WatchEvent<?> event : key.pollEvents()) {

					//获取目录下新增的文件名
					String fileName = event.context().toString();

					//检查文件名是否符合要求
					if (("mashanggou.yml").equals(fileName)) {
						log.debug(fileName + "发生变化");
						Object yaml = YamlUtil.getYaml(filePath);
						if (yaml != null) {
							Map map = (Map) yaml;
							Object o = map.get("sunrizetech");
							if (o != null) {
								Object m = ((Map<String, Object>) o).get("mashanggou");
								if (m instanceof List) {
									data = (List) m;
									log.debug(m);
								}
							}
						}
					}
				}
				key.reset();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}


}
