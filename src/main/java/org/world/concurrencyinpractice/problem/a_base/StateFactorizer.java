package org.world.concurrencyinpractice.problem.a_base;

import org.springframework.web.client.RestTemplate;
import org.world.util.MathUtil;

import javax.annotation.concurrent.NotThreadSafe;
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

/**
 * @Description 无状态的Servlet
 * @Author Double
 */
@NotThreadSafe
@WebServlet(urlPatterns = "/stateFactorizer")
public class StateFactorizer extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String factors = req.getParameter("factors");
		BigInteger i = new BigInteger(factors);
		List<Integer> factor = MathUtil.factor(Long.valueOf(i.toString()));
		PrintWriter writer = resp.getWriter();
		writer.println(factor);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String,String> params = new HashMap<>(1);
		params.put("factors","10");
		String respRet = restTemplate.getForObject("http://localhost:8080/stateFactorizer", String.class, params);
		System.out.println("请求响应结果:"+respRet);
	}
}

