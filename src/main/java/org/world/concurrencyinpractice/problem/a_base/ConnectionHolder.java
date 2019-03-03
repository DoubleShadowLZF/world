package org.world.concurrencyinpractice.problem.a_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description 使用ThreadLocal 来维持线程封闭性，
 * 用于防止对可变的单实例变量（Singleton）或全局变量进行共享。
 */
public class ConnectionHolder {

	private static String DB_URL = "";
	protected Connection initialValue() {
		return null;
	}

	private static  ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>(){
		@Override
		public Connection initialValue(){
			try {
				return DriverManager.getConnection(DB_URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	};

	public static Connection getConnnection(){
		return connectionHolder.get();
	}

}
