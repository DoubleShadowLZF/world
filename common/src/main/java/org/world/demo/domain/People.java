package org.world.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;


/**
 * @Description
 */
@Data @Accessors(chain = true)
public abstract class People {

	private Integer age;
	private String name;
	private String note ;
	private Calendar birthday = Calendar.getInstance();
}
