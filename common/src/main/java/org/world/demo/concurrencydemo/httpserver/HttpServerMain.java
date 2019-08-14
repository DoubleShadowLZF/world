package org.world.demo.concurrencydemo.httpserver;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description SimpleHttpServer 运行类
 * 将设定的HTML页面放置在该目录下，Chrome不支持socket传图片，请使用Firefox
 */
@Slf4j
public class HttpServerMain {
	public static void main(String[] args) throws IOException {

		SimpleHttpServer.setBasePath("D:\\Document\\demo\\world\\common\\src\\main\\java\\org\\world\\demo\\concurrencydemo\\httpserver");
		try {
			SimpleHttpServer.start();
		} catch (Exception e) {
			log.error("{}",e);
		}
	}
}

/*
D:\Document\1.tools\Java\Apache24\bin>ab -n 1000 -c 10 http://localhost:4000/index.html   #每次发送10个请求，总共发送1000次


Server Software:        ouble
Server Hostname:        localhost
Server Port:            4000

Document Path:          /index.html
Document Length:        329 bytes

Concurrency Level:      10
Time taken for tests:   2.173 seconds
Complete requests:      5000
Failed requests:        0
Total transferred:      2015000 bytes
HTML transferred:       1645000 bytes
Requests per second:    2301.40 [#/sec] (mean)
Time per request:       4.345 [ms] (mean)
Time per request:       0.435 [ms] (mean, across all concurrent requests)
Transfer rate:          905.73 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.4      0       4
Processing:     0    4   3.1      3      48
Waiting:        0    3   1.7      2      34
Total:          1    4   3.1      3      49

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      4
  75%      5
  80%      6
  90%      9
  95%     11
  98%     13
  99%     14
 100%     49 (longest request)
 */
