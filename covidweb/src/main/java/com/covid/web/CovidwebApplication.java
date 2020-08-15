package com.covid.web;

import com.covid.web.config.setting.OpenApiSetting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OpenApiSetting.class)
public class CovidwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidwebApplication.class, args);
	}

}
