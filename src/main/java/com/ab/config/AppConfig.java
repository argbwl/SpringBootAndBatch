package com.ab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value= {BatchConfig.class})
public class AppConfig {

}
