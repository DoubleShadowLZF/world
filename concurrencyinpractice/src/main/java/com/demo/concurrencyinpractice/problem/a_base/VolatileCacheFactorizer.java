package com.demo.concurrencyinpractice.problem.a_base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.world.util.MathUtil;

import java.math.BigInteger;

/**
 * @Description
 */
@RestController
@RequestMapping("/VolatileCacheFactorizer")
public class VolatileCacheFactorizer {

	private volatile OneValueCache cache = new OneValueCache(new BigInteger("123"),new BigInteger[]{new BigInteger("123"),new BigInteger("456")});

	@PostMapping
	public Object service(String num,String total){
		BigInteger bigInteger = new BigInteger(num);
		BigInteger[] factors = cache.getFactors(BigInteger.ONE);

		if(factors == null){
			factors = MathUtil.factorB(bigInteger);
			cache = new OneValueCache(bigInteger,factors);
		}
		JSONObject json = new JSONObject();
		json.put("bigInteger",bigInteger);
		json.put("factors",factors);
		return json;
	}
}
