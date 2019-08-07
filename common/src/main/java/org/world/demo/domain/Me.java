package org.world.demo.domain;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.PostConstruct;
import java.util.Calendar;

/**
 * @Description
 */
@Data
@Accessors(chain = true)
public class Me extends Worker {

	//鼻炎
	private String rhintis;

	private String status;

	@Setter()
	private Bicycle bicycle;



	//在Spring进行初始化的时候，会执行后置处理器 PostConstruct
	@PostConstruct
	public void init(){
		this.setName("李卓锋");
		this.getBirthday().set(1995, 07, 01);
		this.setAge(Calendar.getInstance().get(Calendar.YEAR) - this.getBirthday().get(Calendar.YEAR));
	}

	public void rideTo(String addr){

	}

	public void eat(){

	}

	public void work(){

	}

}
