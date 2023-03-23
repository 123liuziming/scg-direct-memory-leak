package com.alibaba.scg;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
public class NettyNativeMemoryMonitor {
	private static final Logger log = LoggerFactory.getLogger(NettyNativeMemoryMonitor.class);

	private static final int MB = 1024 * 1024;
	private static final ScheduledExecutorService memoryTimer = Executors.newSingleThreadScheduledExecutor();
	@PostConstruct
	public void init() {
		memoryTimer.scheduleAtFixedRate(() -> {
			try {
				Field field = PlatformDependent.class.getDeclaredField("DIRECT_MEMORY_COUNTER");
				field.setAccessible(true);
				AtomicLong counter = (AtomicLong) field.get(null);
				field = PlatformDependent.class.getDeclaredField("DIRECT_MEMORY_LIMIT");
				field.setAccessible(true);
				long limit = (long) field.get(null);
				log.info("Used {} MB native memory, limit is {} MB", counter.get() / MB, limit / MB);
			} catch (Exception e) {
				log.error("Error on monitoring netty native memory", e);
			}
		}, 0, 15, TimeUnit.SECONDS);
	}
}
