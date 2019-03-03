package org.world.concurrencyinpractice.problem.a_base;

import javax.annotation.concurrent.Immutable;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @Description 对数值及其因数分解结果进行缓存的不可变容器类
 * 每当需要对一组相关数据以原子方式执行某个操作的时候，
 * 就可以考虑创建一个不可变的类来包含这些数据。
 */
@Immutable
public class OneValueCache {
	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;
	public OneValueCache(BigInteger lastNumber,BigInteger[] factors){
		this.lastNumber = lastNumber;
		this.lastFactors = Arrays.copyOf(factors,factors.length);
	}

	public BigInteger[] getFactors(BigInteger i){
		if(lastNumber == null || !lastNumber.equals(i)){
			return null;
		}else{
			return Arrays.copyOf(lastFactors,lastFactors.length);
		}
	}
}
