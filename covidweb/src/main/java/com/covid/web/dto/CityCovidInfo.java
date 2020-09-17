package com.covid.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CityCovidInfo {
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시", timezone = "Asia/Seoul")
    public Date stdDay;  // 기준 일시

    public int defCnt;  // 확진자 수 TODO : APD에 설명 없음
    public int localOccCnt; // 지역 감염 수  TODO : APD에 설명 없음
    public int overFlowCnt;  // 해외 유입 수 TODO : APD에 설명 없음
    public int isolIngCnt;  // 격리 중인 환자 수   TODO : APD에 설명 없음

    public int seq; // 게시글 번호(국내 시도별 발생현황 고유값)
    public int deathCnt;    // 사망자 수
    public String gubun;   // 시도명(한글)
    public String gubunCn; // 시도명(중국어)
    public String gubunEn; // 시도명(영어)
    public int incDec;  // 전일 대비 증감 수
    public int isolClearCnt;    // 격리 해제 수
    public String qurRate;  // 10만명당 발생률 (gubun이 '검역'인 경우 qurRate가 '-'로 넘어와서 String으로 받음.)

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;    // 등록일시분초

    public Date updateDt;   // 수정일시분초
}
