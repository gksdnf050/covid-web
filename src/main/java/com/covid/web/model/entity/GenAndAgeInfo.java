package com.covid.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class GenAndAgeInfo {
    public String gubun;   // 구분(성별, 연령별)
    public int confCase;    // 확진자
    public int death;   // 사망자
    public float confCaseRate;    // 확진률
    public float deathRate;   // 사망률
    public float criticalRate;    // 치명률

    /* JsonIgnoreProperties */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int seq; // 게시글 번호(확진자 성별, 연령별 고유값)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    public Date createDt;   // 등록일시분초
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date updateDt;   // 수정일시분초   TODO : jsonFormat 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
