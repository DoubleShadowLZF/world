package org.world.concurrencyinpractice.problem.base;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 使内部的可变状态溢出（不要这么做）
 * 本应是私有变量的state，被getSate方法逸出，外部可以直接通过引用变量直接访问到state所指向的内存，
 * 直接修改该块内存的值
 */
public class UnsafeState {

	private String[] state = new String[]{"AK", "AL"};

	private String[] getState() {
		return state;
	}

	public static void main(String[] args) {
		UnsafeState unsafeState = new UnsafeState();
		String[] state = unsafeState.getState();
		for (String s : state) {
			s = null;
		}
		System.out.println(JSONObject.toJSON(state));
		for (int i = 0; i < state.length; i++) {
			state[i] = null;
		}
		System.out.println(JSONObject.toJSON(state));
	}
}
