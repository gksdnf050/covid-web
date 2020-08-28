package com.covid.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class HospitalDto {
    private int id;
    private String hospital;
    private String sido;
    private String sggu;
    private String selectionType;
    private String tel;
    private String typeCode;
    private String x;
    private String y;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date operableDate;
}
