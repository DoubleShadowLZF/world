package org.world.concurrencyinpractice.problem.object;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 基于监视器模式的车辆追踪
 * <p>监视器：<br/>
 * 一个对象可能要访问共享资源来完成它所要提供的服务，在多线程情况下，多个这样的对象在一起就会产生意想不到的后果，比如竞争。
 * 监视器就是在这样的一个对象上设置一个锁，以保证在给定时间内，只有一个线程可以执行这个对象上的任何一个方法。
 * </p>
 * @See DelegatingVehicleTracker
 * @DAuthor Double
 */
public class MonitorVehicleTracker {

	@GuardedBy("this")
	private final Map<String, MutablePoint> locations;

	public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
		this.locations = deepCopy(locations);
	}


	public synchronized Map<String, MutablePoint> getLocations() {
		return deepCopy(locations);
	}

	public synchronized Map<String, MutablePoint> getLocation(String id) {
		MutablePoint loc = locations.get(id);
		return loc == null ? null : new MutablePoint(loc);
	}

	public synchronized void setLocation(String id, int x, int y) {
		MutablePoint loc = locations.get(id);
		if (loc == null) {
			throw new IllegalArgumentException("No sunch ID:" + id);
		}
		loc.x = x;
		loc.y = y;
	}

	private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
		Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
		for (String id : m.keySet()) {
			result.put(id, new MutablePoint(m.get(id)));
		}
		return Collections.unmodifiableMap(result);
	}


	@NotThreadSafe
	private static class MutablePoint extends HashMap {
		public int x, y;

		public MutablePoint() {
			x = 0;
			y = 0;
		}

		public MutablePoint(MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}

}
