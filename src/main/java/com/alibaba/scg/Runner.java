package com.alibaba.scg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
	@Autowired
	private Publisher publisher;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		publisher.applicationContext.publishEvent(new EnableBodyCachingEvent(this, "path_route"));
	}
}
