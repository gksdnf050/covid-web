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
// 백엔드 : resources/mapper 안의 다른 디렉토리에 xml 파일을 넣어두면 인식하지 못하는 에러 처리 -> pattern 수정하여 처리
// 백엔드 : 확진자 정보 API 요청 구현 및 Controller 구현
// 백엔드 : util의 DB로직 Service로 옮기기
// 백엔드 : 조회할 때 ORDER BY 필요한 query 없는 지 확인
// 백엔드 : query parameter 또는 uri에 camel case 쓰는지 확인, 쓰지 않는다면 어떻게 표현하는지 확인 (spinal case 권장, 나는 query 파라미터 부분만 camelCase를 사용함. https://stackoverflow.com/questions/10302179/hyphen-underscore-or-camelcase-as-word-delimiter-in-uris, https://blog.restcase.com/5-basic-rest-api-design-guidelines/ 참조)
// 백엔드 : 감염 현황에 표시할 순서대로 dto 필드 순서 변경
// 백엔드 : paging 제대로 동작하는지 확인

// 프론트 : 주변의 마커만 맵에 찍도록
// 프론트 : 지도 축소 제한
// 프론트 : 프론트에서는 DB의 정보를 요청, 맵에 마커 찍기
// 프론트 : 지도 구성할 때, 로딩창 추가 (또는 로딩 시간을 단축 시킬 방법)
// 프론트 : 로그인 페이지
// 프론트 : 전체, 병원, 식당 메뉴 구현
// 프론트 : 회원가입 페이지 만들기
// 프론트 : 병원, 식당에 따라 마커 이미지 변경
// 프론트 : 감염현황에서 상세메뉴를 클릭했을 때 메뉴의 active class까지 지우는 것 해결하기
// 프론트 : 확진자 정보 표시 구현
// 프론트 : 마커 정보 표시 꾸미기
// 프론트 : 메뉴 리스트의 첫번째 메뉴와 마지막 메뉴는 각각 왼쪽과 오른쪽에만 border-radius 효과를 주어서 클릭했을 때 빈 공간이 생기지 않도록 수정
// 프론트 : 확진자 정보에서 확률 정보는 업 다운 아이콘만 표시
// 프론트 : 출처 표시


// TODO : 백엔드 : 주소 변환 안되는 것 처리.
// TODO : 백엔드 : CovidInfo Api의 updateDt 값 확인 (현재 null값만 오는데 어떤 형식으로 오는지 확인 후 dto에 jsonFormat 추가)
// TODO : 백엔드 : @JsonFormat을 지정한 필드에 null도 들어갈 수 있도록 처리
// TODO : 백엔드 : CovidInfoController와 CovidInfoService에서 반복되는 코드 줄이는 방법 생각
// TODO : 백엔드 : CovidInfo Api는 하루에 한번만 update 되는 건지 확인(domestic 10시, city 10시, age 14시, country 11시 반)

@EnableScheduling
@SpringBootApplication
public class CovidwebApplication {
	public static void main(String[] args) {
		SpringApplication.run(CovidwebApplication.class, args);
	}
}
