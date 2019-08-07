package org.world.demo.utils;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 */
public class SleepUtils {
	public static final void second(long seconds){
		try{
			TimeUnit.SECONDS.sleep(seconds);
		}catch(Exception e){

		}
	}
}
