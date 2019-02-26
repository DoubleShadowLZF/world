package org.world.concurrencyinpractice.problem.base;


import org.world.design.listener.Event;
import org.world.design.listener.EventListener;
import org.world.design.listener.EventSource;

/**
 * @Description 隐式地使this引用逸出（不要这么做）
 */
public class ThisEscape {
	public ThisEscape(EventSource source){
		source.registerListener(new EventListener() {
			@Override
			public void run(Event event) {
				System.out.println("hello world");
			}
		});
	}
}

/**
 * @Description 使用工厂方法来防止this引用在构造过程中逸出
 */

class SafeListener{
	private final EventListener listener;

	private SafeListener(){
		listener = new EventListener() {
			@Override
			public void run(Event event) {
				System.out.println("hello world");
			}
		};
	}

	public static SafeListener newInstance(EventSource source){
		SafeListener safe = new SafeListener();
		source.registerListener(safe.listener);
		return safe;
	}
}
