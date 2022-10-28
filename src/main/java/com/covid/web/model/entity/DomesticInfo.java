package com.covid.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class DomesticInfo {
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    public Date stateDt;    // 기준일
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    public Date stateTime;    // 기준 시간

    public int decideCnt;    // 확진자 수
    public int deathCnt;    // 사망자 수
    public int careCnt;    // 치료중 환자 수
    public int clearCnt;    // 격리 해제 수
    public int examCnt;    // 검사 진행 수
    public int resutlNegCnt;    // 결과 음성 수
    public int accExamCnt;    // 누적 검사 수
    public int accExamCompCnt;    // 누적 검사 완료 수
    public float accDefRate;    // 누적 확진율

    /* json IgnoreProperties */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int seq; // 게시글 번호(감염현황 고유값)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;    // 등록일시분초
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date updateDt;    // 수정일시분초

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
