package com.covid.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CityInfo {
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시", timezone = "Asia/Seoul")
    public Date stdDay;  // 기준 일시
    public String gubun;   // 시도명(한글)

    public int defCnt;  // 확진자 수 TODO : API에 설명 없음
    public int incDec;  // 전일 대비 증감 수
    public int deathCnt;    // 사망자 수
    public int localOccCnt; // 지역 감염 수  TODO : API에 설명 없음
    public int overFlowCnt;  // 해외 유입 수 TODO : API에 설명 없음
    public int isolIngCnt;  // 격리 중인 환자 수   TODO : API에 설명 없음
    public int isolClearCnt;    // 격리 해제 수

    public String qurRate;  // 10만명당 발생률 (gubun이 '검역'인 경우 qurRate가 '-'로 넘어와서 String으로 받음.)

    /* ignoreProperties */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // https://stackoverflow.com/questions/12505141/only-using-jsonignore-during-serialization-but-not-deserialization 참조 (JsonIgnoreProperties를 사용하면 serialize, deserialize 모두 ignore 됨)
    public int seq; // 게시글 번호(국내 시도별 발생현황 고유값)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String gubunCn; // 시도명(중국어)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String gubunEn; // 시도명(영어)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;    // 등록일시분초
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date updateDt;   // 수정일시분초
}
