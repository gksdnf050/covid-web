package com.covid.web;

import com.covid.web.config.setting.OpenApiSetting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

// 백엔드 : application.properties의 값을 ApiUtil 클래스에서 사용하도록
// 백엔드 : 안심식당, 안심병원 정보 하루에 한번 scheduler로 db에 저장
// 백엔드 : spring security 로그인 구현
// 백엔드 : 로그인 실패 메시지 포워딩으로 구현 -> redirect로 하는 대신 flashMap 사용 (로그인 요청이 Post인데, login 페이지 요청은 Get방식이므로 포워딩 시 error 발생)

// 프론트 : 주변의 마커만 맵에 찍도록
// 프론트 : 지도 축소 제한
// 프론트 : 프론트에서는 DB의 정보를 요청, 맵에 마커 찍기
// 프론트 : 지도 구성할 때, 로딩창 추가 (또는 로딩 시간을 단축 시킬 방법)
// 프론트 : 로그인 페이지
// 프론트 : 전체, 병원, 식당 메뉴 구현
// 프론트 : 회원가입 페이지 만들기
// 프론트 : 병원, 식당에 따라 마커 이미지 변경

// TODO : 백엔드 : 주소 변환 안되는 것 처리.
// TODO : 백엔드 : 확진자 정보 API 요청 구현 및 Controller 구현

// TODO : 프론트 : 확진자 정보 표시 구현
// TODO : 프론트 : 마커 정보 표시 꾸미기
// TODO : 프론트 : 출처 표시

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(OpenApiSetting.class)	// https://javacan.tistory.com/entry/springboot-configuration-properties-class 참조
public class CovidwebApplication {
	public static void main(String[] args) {
		SpringApplication.run(CovidwebApplication.class, args);
	}
}
