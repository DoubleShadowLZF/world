package org.world.demo.domain;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

/**
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeTests {

	@Autowired
	Me me;

	@Test
	public void testAge(){
//		Me me = new Me();

		me.getBirthday().set(1995, 07, 01);
		me.setAge(Calendar.getInstance().get(Calendar.YEAR) - me.getBirthday().get(Calendar.YEAR));

		System.out.println(me.getAge());
	}

//	@Test
//	public void test(){
//		BeanUtils.copyProperties();
//	}
}
