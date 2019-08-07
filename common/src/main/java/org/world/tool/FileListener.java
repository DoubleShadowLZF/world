package org.world.tool;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * @Description
 */
public class FileListener {
	//监听文件
	public static void listenFile(String filePath) throws IOException {
		// 需要监听的文件目录（只能监听目录）
//		String path = "d:/test";

		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path p = Paths.get(filePath);
		p.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE);


		Thread thread = new Thread(() -> {
			try {
				while(true){
					WatchKey watchKey = watchService.take();
					List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
					for(WatchEvent<?> event : watchEvents){
						//TODO 根据事件类型采取不同的操作。。。。。。。
						System.out.println("["+filePath+"/"+event.context()+"]文件发生了["+event.kind()+"]事件");
						if(event.context().toString().equals("新建文本文档.txt")){
							System.out.println("文档被监听");
						}
					}
					watchKey.reset();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.setDaemon(false);
		thread.start();

		// 增加jvm关闭的钩子来关闭监听
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				watchService.close();
			} catch (Exception e) {
			}
		}));
	}



	public static void main(String[] args) throws IOException {
		FileListener.listenFile("D:\\temp");
	}

}
