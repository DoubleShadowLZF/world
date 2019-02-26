package org.world.demo.concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入/输出流
 *
 * @Description 管道输入/输出流和普通的文件输入/输出流或者网络输入/输出流不同之处在于，
 * 它主要用于线程之间的数据传输，而传输的媒介为内存。
 * 面向字节： PipedOutputStream 和 PipedInputStream
 * 面向字符： PipedReader 和 PipedWriter
 * <p>
 * 功能：接受main线程的输入，任何main线程的输入均通过PipedWriter写入，
 * 而printThread在另一端通过PipedReader将内容独处并打印。
 * </p>
 */
public class Piped {
	public static void main(String[] args) throws IOException {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		//将输出流和输入流进行连接，否则在使用时会抛出IOException
		out.connect(in);
		Thread printThread = new Thread(new Print(in), "PrintThread");
		printThread.start();
		int receive = 0;
		System.out.println("请输入信息：");
		try {
			while ((receive = System.in.read()) != -1) {
				//通过读取控制台输入，写入到 pipedReader中
				out.write(receive);
			}
		} finally {
			out.close();
		}
	}

	static class Print implements Runnable {
		private PipedReader in;

		public Print(PipedReader in) {
			this.in = in;
		}

		@Override
		public void run() {
			int receive = 0;
			try {
				while ((receive = in.read()) != -1) {
					//将PipedReader读取到内容，打印在控制台上
					System.out.print((char) receive);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
