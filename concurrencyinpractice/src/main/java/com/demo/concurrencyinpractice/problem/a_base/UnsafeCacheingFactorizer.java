package com.demo.concurrencyinpractice.problem.a_base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.world.demo.utils.ThreadPoolUtil;
import org.world.model.Contant;
import org.world.util.MathUtil;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description 该Servlet在没有足够原子性保证的情况下对其最近计算结果进行缓存
 * @Author Double
 */
@NotThreadSafe
@RestController
@RequestMapping("/UnsafeCacheingFactorizer")
public class UnsafeCacheingFactorizer {
	/**
	 * 最后一次被分解的因数
	 */
	private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
	/**
	 * 因数分解得到的因数
	 */
	private final AtomicReference<List<BigInteger>> lastFactors = new AtomicReference<>();

	@GetMapping
	public Object service(HttpServletRequest request) {
		String num = request.getParameter("num");
		//尽管 lastNumber 和 lastFactors 是原子变量，但是在begin和end无法保证并发时执行时机前后对lastNumber和lastFactors实时更新，此处数据会有问题
		if (num.equals(lastNumber.get())) {
			return lastFactors.get();
		} else {
			List<BigInteger> factor = MathUtil.factor(BigInteger.valueOf(Long.valueOf(num)));
			//begin
			lastNumber.set(BigInteger.valueOf(Long.valueOf(num)));
			lastFactors.set(factor);
			//end
			return factor;
		}
	}

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();

		for (int i = 0; i < 100; i++) {
			final int temp = i;
			ThreadPoolUtil.RunThread(() -> {

				String result = restTemplate.getForObject(Contant.URL_HEADER + "/UnsafeCacheingFactorizer?num=" + temp, String.class);
				System.out.println(result);
			}, 100, 2);
		}

	}


}

/**
 * @Description 内置锁
 * 这个Servlet能正确地缓存最新的计算结果，但是并发性却非常糟糕（不要这么做）
 * @Author Double
 */
@ThreadSafe
@RestController
@RequestMapping("/SynchronizedFactorizer")
class SynchronizedFactorizer {
	/**
	 * 最后一次被分解的因数
	 */
	@GuardedBy("this")
	private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
	/**
	 * 因数分解得到的因数
	 */
	@GuardedBy("this")
	private final AtomicReference<List<BigInteger>> lastFactors = new AtomicReference<>();

	@GetMapping
	public synchronized Object service(HttpServletRequest request) {
		String num = request.getParameter("num");
		//尽管 lastNumber 和 lastFactors 是原子变量，但是在begin和end无法保证并发时执行时机前后对lastNumber和lastFactors实时更新，此处数据会有问题
		if (num.equals(lastNumber.get())) {
			return lastFactors.get();
		} else {
			List<BigInteger> factor = MathUtil.factor(BigInteger.valueOf(Long.valueOf(num)));
			//begin
			lastNumber.set(BigInteger.valueOf(Long.valueOf(num)));
			lastFactors.set(factor);
			//end
			return factor;
		}
	}
}

@ThreadSafe
@RestController
@RequestMapping("/SynchronizedFactorizer")
class CachedFactorizer {
	/**
	 * 因数分解得到的因数
	 */
	@GuardedBy("this")
	private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
	/**
	 * 因数分解得到的因数
	 */
	@GuardedBy("this")
	private final AtomicReference<List<BigInteger>> lastFactors = new AtomicReference<>();
	/**
	 * 命中计数器
	 */
	@GuardedBy("this")
	/**
	 * 缓存命中计数器
	 */
	private long hits;
	@GuardedBy("this")
	private long cacheHits;

	public synchronized long getHits() {
		return hits;
	}

	public synchronized double getCacheHitRatio() {
		return (double) cacheHits / (double) hits;
	}

	@GetMapping("CachedFactorizerService")
	public Object service(HttpServletRequest request) {
		String num = request.getParameter("num");
		List<BigInteger> factors = null;
		synchronized (this) {
			++hits;
			if (num.equals(lastNumber.get())) {
				++cacheHits;
				List<BigInteger> bigIntegers = lastFactors.get();
				List<BigInteger> cloneFactors = new ArrayList<>();
				for (BigInteger n : bigIntegers) {
					cloneFactors.add(n);
				}
				factors = cloneFactors;
			}
		}
		if (factors == null) {
			factors = MathUtil.factor(BigInteger.valueOf(Long.valueOf(num)));
			synchronized (this) {
				lastNumber.set(BigInteger.valueOf(Long.valueOf(num)));
				lastFactors.set(factors);
			}
		}
		return factors;
	}
}
