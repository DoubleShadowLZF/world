package com.demo.concurrencyinpractice.problem.b_object;

import org.world.model.Person;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 通过封闭机制来确保线程安全
 * @DAuthor Double
 */
@ThreadSafe
public class PersonSet {
	@GuardedBy("this")
	private final Set<Person> mySet = new HashSet<>();

	public synchronized void addPerson(Person p){
		mySet.add(p);
	}

	public synchronized boolean containsPerson(Person p){
		return mySet.contains(p);
	}
}
