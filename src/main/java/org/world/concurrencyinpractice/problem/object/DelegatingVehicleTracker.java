package org.world.concurrencyinpractice.problem.object;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 基于委托的车辆追踪器
 * 将线程安全委托给ConcurrentHashMap
 * <p>委托模式<br/>
 * 有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理。
 * </p>
 * @See MonitorVehicleTracker
 * @DAuthor Double
 */
public class DelegatingVehicleTracker {

	private final ConcurrentHashMap<String, Point> locations;
	private final Map<String, Point> unmodifiableMap;

	public DelegatingVehicleTracker(Map<String, Point> points) {
		locations = new ConcurrentHashMap<>(points);
		unmodifiableMap = Collections.unmodifiableMap(locations);
	}

	/**
	 * 在使用监视器模式的车辆追踪器中返回的是车辆位置的快照，
	 * 而在使用委托的车辆追踪器中返回的是一个不可修改但却试试的车辆位置视图。
	 * 这意味着，如果线程A调用getLocations，而线程B在随后修改了某些点的位置，
	 * 那么在返回给线程A的Map中将反映出这些变化（可能导致不一致的车辆位置视图）。
	 * @return
	 */
	/*public Map<String,Point> getLocations(){
		return unmodifiableMap;
	}*/

	/**
	 * 返回locations的静态拷贝而非实时拷贝
	 * @return
	 */
	public Map<String, Point> getLocaitons() {
		return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
	}

	public Point getLocation(String id) {
		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {
		if (locations.replace(id, new Point(x, y)) == null) {
			throw new IllegalArgumentException("invalid vehicle name:" + id);
		}
	}

	/**
	 * 由于Point类是不可变的，因而它是线程安全的。
	 * 不可变类的值可以被自由的共享与发布，
	 * 因此在返回location时不需要复制。
	 */
	@Immutable
	public class Point {
		public final int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
