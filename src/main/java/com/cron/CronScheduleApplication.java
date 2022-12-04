package com.cron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages="com.cron")
@EnableWebMvc
@ComponentScan(basePackages = {"com.cron.controller","com.cron.dao","com.cron.service","com.cron"})
@EnableAutoConfiguration
//@EntityScan("com.cron.entity")
//@EnableJpaRepositories("com.cron.dao")
public class CronScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronScheduleApplication.class, args);
	}

}
