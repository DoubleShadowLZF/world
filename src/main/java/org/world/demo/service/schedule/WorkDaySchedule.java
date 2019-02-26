package org.world.demo.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.world.demo.domain.Me;

/**
 * @Description
 */
@Service
public class WorkDaySchedule {

	@Autowired
	private Me me;

	@Scheduled( cron = "0 * * * * ?" )
	public void work(){
		me.rideTo("黄埔创新大厦 ");
		me.eat();

		me.work();
	}
}
