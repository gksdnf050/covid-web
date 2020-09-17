package com.covid.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AgeCovidInfo {
    public int seq; // 게시글 번호(확진자 성별, 연령별 고유값)
    public String gubun;   // 구분(성별, 연령별)
    public int confCase;    // 확진자
    public float confCaseRate;    // 확진률
    public int death;   // 사망자
    public float deathRate;   // 사망률
    public float criticalRate;    // 치명률

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;   // 등록일시분초
    public Date updateDt;   // 수정일시분초   TODO : jsonFormat 추가
}
