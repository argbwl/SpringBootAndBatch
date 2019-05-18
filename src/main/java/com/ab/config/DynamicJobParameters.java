package com.ab.config;

import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicJobParameters implements JobParametersIncrementer {
	Date date = null;
	
	@Bean(name="jobParameter")
	public JobParameters getNext(JobParameters parameters) {
		date = new Date();
		parameters = new JobParametersBuilder().addLong("currentTime", new Long(System.currentTimeMillis()))
				.toJobParameters();
		return parameters;
	}
}