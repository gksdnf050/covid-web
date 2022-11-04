package com.covid.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class CountryInfo {
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시", timezone = "Asia/Seoul")
    public Date stdDay; // 기준 일시

    public String nationNm; // 국가명
    public String areaNm; // 지역명
    public int natDefCnt; // 국가별 확진자 수
    public int natDeathCnt; // 국가별 사망자 수
    public float natDeathRate; // 확진률 대비 사망률

    /* ignoreProperties */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int seq; // 게시글 번호(국외 발생 현황 고유값)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String areaNmEn; // 지역명(영문)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String areaNmCn; // 지역명(중문)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nationNmEn; // 국가명(영문)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nationNmCn; // 국가명(중문)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt; // 등록일시분초
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date updateDt; // 수정일시분초   TODO : JsonFormat
}
