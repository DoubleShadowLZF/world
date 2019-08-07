package org.world.model;

import lombok.Data;

/**
 * @Description
 */
@Data
public class Animal {

	public int priority = 0;

	public String name;

	public Animal(String name){
		this.name = name;
	}

	public Animal(Integer priority,String name){
		this.priority = priority;
		this.name = name;
	}

	public boolean isPotentialMate(Animal a){
		return a.priority == this.priority;
	}


}
