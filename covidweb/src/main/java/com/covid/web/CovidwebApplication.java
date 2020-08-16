package com.covid.web;

import com.covid.web.config.setting.OpenApiSetting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// TODO: 지도 구성할 때, 로딩창 추가 (또는 로딩 시간을 단축 시킬 방법)
// TODO: application.properties의 값을 ApiUtil 클래스에서 사용하도록

@SpringBootApplication
@EnableConfigurationProperties(OpenApiSetting.class)	// https://javacan.tistory.com/entry/springboot-configuration-properties-class 참조
public class CovidwebApplication {
	public static void main(String[] args) {
		SpringApplication.run(CovidwebApplication.class, args);
	}
}
