package com.ab;

import java.sql.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.ab.config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class SpringBootWithBatchApplication {
	@Autowired
	private static JobLauncher launcher;

	public static void main(String[] args) {
		ApplicationContext ctx = null;
		JobExecution execution = null;
		Job job = null;
		ctx = SpringApplication.run(SpringBootWithBatchApplication.class, args);
		// get Bean
		job = ctx.getBean("job1", Job.class);
		long start = System.currentTimeMillis();
		System.out.println(start);
		JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new java.util.Date())
                .addLong("time",System.currentTimeMillis()).toJobParameters();
		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date(start));
//			execution = launcher.run(job, new JobParameters());
			execution = launcher.run(job, jobParameters);
			System.out.println("status::" + execution.getStatus());
			
			long end = System.currentTimeMillis();
			System.out.println(end - start);
			//TODO Handling NPE at Runtime
		} catch (Exception e) {
			System.out.println("error msg : "+e.getMessage());
			e.printStackTrace();
		}

		// close container
		((ConfigurableApplicationContext) ctx).close();
	}
}
