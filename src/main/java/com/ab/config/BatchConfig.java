package com.ab.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ab.model.IBank;
import com.ab.model.OBank;
import com.ab.processor.BankItemProcessor;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages="com.ab")
public class BatchConfig {
	@Autowired
	private DataSource ds;
	@Autowired
	private  JobBuilderFactory  jobBuilderFactory;
	@Autowired
	private  StepBuilderFactory stepBuilderFactory;
	@Autowired
	private  BankItemProcessor  processor;
	
	
	 //ItemReader
	  @Bean
	  public FlatFileItemReader<IBank> reader() {
		  
	    FlatFileItemReader<IBank> reader; 
	    reader= new FlatFileItemReader<>();
	    
	    reader.setResource(new ClassPathResource("data/bank.txt"));
	    
	    reader.setLineMapper(new DefaultLineMapper<IBank>() {{
	      setLineTokenizer(new DelimitedLineTokenizer() {{
	        setNames(new String[]{"bankId","bankName","ifsCode","estYear"});
	      }});
	      
	      setFieldSetMapper(new BeanWrapperFieldSetMapper<IBank>() {{
	        setTargetType(IBank.class);
	      }});
	    }});
	    return reader;
	  }//reader()
	  
	//Item Writer
	  @Bean
	  public JdbcBatchItemWriter<OBank> writer() {
	    JdbcBatchItemWriter<OBank> writer =null;
	    writer=	new JdbcBatchItemWriter();

	    writer.setDataSource(ds);
	    writer.setSql("INSERT INTO BANK (bank_id,bank_name,ifs_code,est_year)  VALUES (:bankId,:bankName,:ifsCode,:estYear)");
	    writer.setItemSqlParameterSourceProvider(
	        new BeanPropertyItemSqlParameterSourceProvider<OBank>());
	    return writer;
	  }//writer()
	  
	  
	  @Bean("job1")
	  public Job createJob() {
	    return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer())
	        .flow(createStep()).end().build();
	  }
	  
	  @Bean("step1")
	  public Step createStep() {
	    return stepBuilderFactory.get("step1").<IBank, OBank>chunk(1000).reader(reader())
	        .processor(processor).writer(writer()).build();
	  }
	  

	

}
