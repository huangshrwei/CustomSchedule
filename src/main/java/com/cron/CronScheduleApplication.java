package com.cron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(scanBasePackages="com.cron")
@EnableWebMvc
@ComponentScan(basePackages = {"com.cron.controller","com.cron.dao","com.cron.service","com.cron"})
@EnableAutoConfiguration
public class CronScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronScheduleApplication.class, args);
	}

}
