package org.world.concurrencyinpractice.problem.base;

/**
 * @Description 不安全发布示例
 * “发布（publish）”一个对象的意思是指，使对象能够在当前作用域之外的代码中使用
 * @Author Double
 */
public class Holder {
	/**
	 * 不安全的发布
	 */
	public Holder holder ;

	private Integer n;

	public Holder(Integer n) {
		this.n = n;
	}

	/**
	 * 其他线程看到线程尚未创建完成的对象
	 */
	public void intialize(){
		holder = new Holder(42);
	}

	/**
	 * 未被正确发布：
	 * 首先，除了发布对象的线程外，其他线程可以看到的Holder域是一个失效值，因此将看到一个空引用或者之前的旧值。
	 * 然而，更糟糕的情况是，线程看到Holder引用的值是最新的，但Holder状态的值却是失效的。
	 * 情况变得更加不可预测的是，某个线程在第一次读取域时得到失效值，而再次读取这个域时会得到一个更新值，
	 * 这也是assertSainty抛出AssertionError的原因。
	 * 如果没有足够的同步，那么当在多个线程间共享数据时将发生一些非常奇怪的事情。
	 */
	public void assertSanity(){
		if(n != n){
			throw  new AssertionError("This statement is false.");
		}
	}
}
