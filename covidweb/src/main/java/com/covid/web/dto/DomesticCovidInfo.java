package com.covid.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DomesticCovidInfo {
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    public Date stateDt;    // 기준일

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    public Date stateTime;    // 기준 시간

    public int seq; // 게시글 번호(감염현황 고유값)
    public int decideCnt;    // 확진자 수
    public int clearCnt;
    public int examCnt;    // 검사진행 수
    public int deathCnt;    // 사망자 수
    public int careCnt;    // 치료중 환자 수
    public int resutlNegCnt;    // 결과 음성 수
    public int accExamCnt;    // 누적 검사 수
    public int accExamCompCnt;    // 누적 검사 완료 수
    public float accDefRate;    // 누적 확진율

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;    // 등록일시분초

    public Date updateDt;    // 수정일시분초
}
