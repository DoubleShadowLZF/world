package org.world.demo.socket.file;

import org.springframework.cache.annotation.Cacheable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * @Description
 */
public class ServerTcpListener implements Runnable {
	@Override
	public void run() {

	}

	public static void receiveFile(Socket socket) {
		byte[] inputByte = null;
		int length = 0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
			fos = new FileOutputStream(new File("D:\\Document\\demo\\world\\src\\main\\java\\org\\world\\demo\\socket\\file\\"+ Calendar.getInstance().getTimeInMillis() +".jpg"));
			inputByte = new byte[1024];
			System.out.println("开始接收数据...");
			while((length =dis.read(inputByte,0,inputByte.length))>0 ){
				System.out.println(length);
				fos.write(inputByte,0,length);
				fos.flush();
			}
			System.out.println("完成接收");
		} catch (Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if(dis != null){
					dis.close();
				}
				if(socket != null){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			final ServerSocket server = new ServerSocket(4000);
			Thread th = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							System.out.println("开始监听....");
							Socket socket = server.accept();
							System.out.println("有连接");
							receiveFile(socket);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			th.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
