package com.demo.concurrencyinpractice.problem.a_base;

import org.springframework.web.client.RestTemplate;
import org.world.demo.utils.ThreadPoolUtil;
import org.world.model.Contant;
import org.world.util.MathUtil;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 没有同步的情况下统计已处理请求数量的Servlet
 */
@NotThreadSafe
@WebServlet(urlPatterns = "/UnsafeCountingFactorizer")
public class UnsafeCountingFactorizer extends HttpServlet {
	private long count = 0;

	public long getCount() {
		return count;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String num = req.getParameter("num");
		if (num == null || num.length() == 0) {
			return;
		}
		BigInteger i = new BigInteger(num);
		List<Integer> factor = MathUtil.factor(Long.valueOf(i.toString()));
		PrintWriter writer = resp.getWriter();
		++count;
		writer.println(count + ":" + factor);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) {

		Map<String, String> params = new HashMap<>(1);
		params.put("num", "900");
		RestTemplate restTemplate = new RestTemplate();
		String url = Contant.URL_HEADER + "UnsafeCountingFactorizer?num=900";

		ThreadPoolUtil.RunThread(() -> {
			String respRet = restTemplate.getForObject(url, String.class, params);
			System.out.println(respRet);
		}, 100, 3);
	}
}

@ThreadSafe
@WebServlet(urlPatterns = "/CountingFactorizer")
class CountingFactorizer extends HttpServlet {
	/**
	 * 使用原子变量类AtomicLong，保证对count操作都是原子操作
	 */
	private final AtomicLong count = new AtomicLong(0);

	public long getCount() {
		return count.get();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BigInteger i = new BigInteger(req.getParameter("num"));
		List<Integer> factor = MathUtil.factor(Long.valueOf(i.toString()));
		count.incrementAndGet();
		PrintWriter writer = resp.getWriter();
		writer.println(getCount()+":"+factor);
	}

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();

		ThreadPoolUtil.RunThread(()->{
			String respResult = restTemplate.getForObject(Contant.URL_HEADER + "CountingFactorizer?num=900", String.class);
			System.out.println(respResult);
		},100,3);
	}
}
