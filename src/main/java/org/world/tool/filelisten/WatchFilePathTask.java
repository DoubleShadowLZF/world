package org.world.tool.filelisten;

import ch.qos.logback.core.util.FileUtil;
import com.esotericsoftware.yamlbeans.YamlException;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.world.util.file.YamlUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

/**
 * 监听 “yamlfile.yml”文件变化，动态加载
 *
 * @author cancer
 */
public class WatchFilePathTask extends Thread {
	private Log log = LogFactory.getLog(WatchFilePathTask.class);

	private String listenPath = System.getProperty("user.dir");
	private String listenFile = "yamlfile.yml";
	private String filePath = listenPath + File.separator + listenFile;

	private WatchService watchService;

	private List<String> data;

	public List<String> getData(){
		return this.data;
	}

	public WatchFilePathTask() throws IOException, URISyntaxException {
		ClassPathResource resource = new ClassPathResource(listenFile);
		InputStream inputStream = resource.getInputStream();
		File distFile = new File(filePath);
		if (!distFile.exists()) {
			distFile.createNewFile();
		}

		FileUtils.copyInputStreamToFile(inputStream,distFile);
		update();
	}

	@Override
	public void run() {
		try {
			//获取监控服务
			watchService = FileSystems.getDefault().newWatchService();
			log.debug("获取监控服务" + watchService);
			Path path = Paths.get(listenPath);
			log.debug("码上购文件监听路径：" + listenPath+";监听文件路径："+filePath);

			//注册监控服务，监控新增事件
			WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				for (WatchEvent<?> event : key.pollEvents()) {
					//获取目录下修改的文件名
					String fileName = event.context().toString();

					//检查文件名是否符合要求
					if ((listenFile).equals(fileName)) {
						log.debug(fileName + "发生变化");
						update();
					}
				}
				key.reset();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void update() throws FileNotFoundException, YamlException {
		Object yaml = YamlUtil.getYaml(filePath);
		if (yaml != null) {
			Map map = (Map) yaml;
			Object o = map.get("yaml");
			if (o != null) {
				Object m = ((Map<String, Object>) o).get("app-id");
				if (m instanceof List) {
					data = (List) m;
					log.debug(m);
				}
			}
		}
	}
}
