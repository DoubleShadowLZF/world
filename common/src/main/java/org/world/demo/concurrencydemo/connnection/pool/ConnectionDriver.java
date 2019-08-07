package org.world.demo.concurrencydemo.connnection.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @Description Connection 的代理实现仅仅是在commit()方法调用时休眠100ms
 */
public class ConnectionDriver {
	static class ConnectionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.MICROSECONDS.sleep(100);
			}
			return null;
		}
	}

	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(), new Class<?>[]{Connection.class}, new ConnectionHandler());
	}
}
