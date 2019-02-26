package org.world.design.listener;

/**
 * 事件源
 */
public class EventSource {
	private EventListener eventListener;

	public static void main(String[] args) {
		EventSource eventSource = new EventSource();
		eventSource.registerListener(new EventListener() {
			@Override
			public void run(Event event) {
				System.out.println(event.getEventSource());
			}
		});

		eventSource.run();
	}

	public void registerListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	public void run() {
		eventListener.run(new Event(this));
	}

	@Override
	public String toString() {
		return "我是事件源";
	}
}

