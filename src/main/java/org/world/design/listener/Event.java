package org.world.design.listener;

/**
 * 事件
 */
public class Event {
	private EventSource eventSource;

	public Event(EventSource eventSource) {
		this.eventSource = eventSource;
	}

	public EventSource getEventSource() {
		return eventSource;
	}
}
