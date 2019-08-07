package org.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description
 */
@SpringBootApplication
@ServletComponentScan//扫描所有带WebServlet注解的类
public class WorldApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorldApplication.class,args);
	}
}
