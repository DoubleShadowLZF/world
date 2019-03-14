package com.demo.concurrencyinpractice.problem.a_base;

import java.nio.IntBuffer;

/**
 * @Description 静态缓冲区：ByteBuffer、ShortBuffer、IntBuffer、CharBuffer、FloatBuffer、DoubleBuffer、LongBuffer
 * @link https://w0ww.cnblogs.com/chenpi/p/6475510.html
 */
public class StaticBuffer {
	public static void main(String[] args) {
		IntBuffer intBuffer = IntBuffer.allocate(100);
	}
}
