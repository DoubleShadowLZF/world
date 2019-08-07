package org.world.demo.config;

import org.springframework.context.annotation.Bean;
import org.world.demo.domain.Me;

/**
 * @Description 全局配置
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

	/**
	 * 创建一个“我”的实例对象
	 * @return
	 */
	@Bean
	public Me me(){
		Me me = new Me();
		return me;
	}
}
