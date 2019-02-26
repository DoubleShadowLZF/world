package org.world.demo.concurrencydemo.httpserver;

import org.world.demo.concurrencydemo.thread.pool.DefaultThreadPool;
import org.world.demo.concurrencydemo.thread.pool.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 一个基于线程池技术的简单Web服务器
 * 该Web服务器用来处理HTTP请求，目前只能处理简单的文本和JPG图片内容。
 * 这个Web服务器用来处理HTTP请求，目前只能处理简单的文本和JPG图片内容。
 * 这个Web服务器使用main线程不断地接受客户端Socket的连接，
 * 将连接以及请求交给线程池处理，这样使得Web服务器能够同时处理多个客户端请求。
 * PS : 使用Chrome浏览器不能显示图片，使用Firefox浏览器则可以正常显示
 */
public class SimpleHttpServer {
	/**
	 * 处理HttpRequest的线程池
	 */

	static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(11);

	/**
	 * SimpleHttpServer 的根路径
	 */
	static String basePath;

	static ServerSocket serverSocket;

	/**
	 * 服务监听端口
	 */
	static int port = 4000;

	public static void setPort(int port) {
		if (port > 0) {
			SimpleHttpServer.port = port;
		}
	}

	public static void setBasePath(String basePath) {
		if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
			SimpleHttpServer.basePath = basePath;
		}
	}

	//启动SimpleHttpServer
	public static void start() throws Exception {
		serverSocket = new ServerSocket(port);
		Socket socket = null;
		while ((socket = serverSocket.accept()) != null) {
			//接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
			threadPool.execute(new HttpRequestHandler(socket));
		}
		serverSocket.close();
	}

	static class HttpRequestHandler implements Runnable {
		private Socket socket;

		public HttpRequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			BufferedReader reader = null;
			PrintWriter out = null;
			InputStream in = null;
			DataOutputStream dos = null;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String header = reader.readLine();
				//由相对路径计算出绝对路径
				String filePath = basePath + header.split(" ")[1];
				out = new PrintWriter(socket.getOutputStream());
				//如果请求资源的后缀为jpg或者ico，则读取资源并输出
				if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
					in = new FileInputStream(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i = 0;
					while ((i = in.read()) != -1) {
						baos.write(i);
					}
					byte[] array = baos.toByteArray();
					out.println("HTTP/1.1 200 OK");
					out.println("Server:Double");
					out.println("Content-Type: image/jpeg");
					out.println("Content-Length: " + array.length);
					out.println("");
//					dos = new DataOutputStream(socket.getOutputStream());
//					dos.write(array, 0, array.length);
					socket.getOutputStream().write(array, 0, array.length);
					socket.shutdownOutput();
				} else {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
					out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Server:Double");
					out.println("Content-Type: text/html; charset=UTF-8");
					out.println("");
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
				}
				out.flush();
			} catch (Exception e) {
				out.println("HTTP/1.1 500");
				out.println("");
				out.flush();
			} finally {
				close(br, in, reader, out, socket, dos);
			}
		}
	}

	/**
	 * 关闭流或者Socket
	 */

	private static void close(Closeable... closeables) {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				try {
					closeable.close();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
				//此处不能只是简答地使用 IOException ，
				// 因此关闭的资源中有可能本身的对象引用变量为空，此时外抛空指针异常，
				//socket连接将不会释放
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
