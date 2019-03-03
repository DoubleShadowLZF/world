package org.world.concurrencyinpractice.problem.b_object;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 安全发布底层状态的车辆追踪器
 * 将其线程安全性委托给底层的ConcurrentHashMap，只是Map中的元素是线程安全的且不可变的Point，并非不可变的。
 * getLocation 方法返回底层Map对象的一个不可变副本。调用者不能增加或删除车辆，
 * 但却可以通过修改返回Map中的SafePoint值来改变车辆的位置。
 * @DAuthor Double
 */
@ThreadSafe
public class PublishingVehicleTracker {

	private final Map<String, SafePoint> locations;

	private final Map<String, SafePoint> unmodifiableMap;

	public PublishingVehicleTracker(Map<String, SafePoint> locations) {
		this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
		this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
	}

	public Map<String, SafePoint> getLocations() {
		return unmodifiableMap;
	}

	public SafePoint getLocation(String id) {
		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {
		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException("invalid vehicle name:" + id);
		}
		locations.get(id).set(x, y);
	}

	public static class SafePoint {
		@GuardedBy("this")
		private int x, y;

		private SafePoint(int[] a) {
			this(a[0], a[1]);
		}

		public SafePoint(SafePoint p) {
			this(p.get());
		}

		public SafePoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public synchronized int[] get() {
			return new int[]{x, y};
		}

		public synchronized void set(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
