package com.covid.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CovidHospital {
    private String hospital;    // 기관명
    private String sido;    // 시도명
    private String sggu;    // 시군구명
    private String selectionType;   // 선정유형
    private String tel; // 전화번호
    private String typeCode;    // 구분 코드
    private String x;   // x좌표
    private String y;   // y좌표

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date operableDate;  // 운영가능일자

    public CovidHospital(String hospital, String sido, String sggu, String selectionType, String tel, String typeCode, String x, String y, Date operableDate) {
        this.hospital = hospital;
        this.sido = sido;
        this.sggu = sggu;
        this.selectionType = selectionType;
        this.tel = tel;
        this.typeCode = typeCode;
        this.x = x;
        this.y = y;
        this.operableDate = operableDate;
    }
}
