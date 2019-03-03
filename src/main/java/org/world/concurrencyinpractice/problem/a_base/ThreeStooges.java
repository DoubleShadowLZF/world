package org.world.concurrencyinpractice.problem.a_base;

import javax.annotation.concurrent.Immutable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 在可变对象的基础上构建不可变类
 * 对象不可变条件：
 * 1）对象创建以后其状态就不能修改；
 * 2）对象的所有域都是final类型；
 * 3）对象时正确创建的（在对象的创建期间，this引用没有逸出）
 */
@Immutable
public class ThreeStooges {

	private final Set<String> stooges = new HashSet<>();

	public ThreeStooges(){
		stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
	}

	public boolean isStooge(String name){
		return stooges.contains(name);
	}

	public static void main(String[] args) {
		ThreeStooges threeStooges = new ThreeStooges();
		System.out.println(threeStooges.isStooge("Moe"));
		System.out.println(threeStooges.isStooge("Double"));

		String obj = "address";
		final String finalObj = obj;
		obj += "1";
		System.out.println(finalObj == obj);
		final ThreeStooges t = threeStooges;
		System.out.println(threeStooges == t);
		Integer i = 0;
		final Integer fi = i;
		System.out.println(i == fi);
	}
}
