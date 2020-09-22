package com.covid.web.dto.relaxInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
public class RelaxHospital {
    private String yadmNm;    // 기관명
    private String sidoNm;    // 시도명
    private String sgguNm;    // 시군구명
    private String hospTyTpCd;   // 선정유형
    private String telno; // 전화번호
    private String spclAdmTyCd;    // 구분코드
    private String x;   // x좌표
    private String y;   // y좌표

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date adtFrDd;  // 운영가능일자
}
