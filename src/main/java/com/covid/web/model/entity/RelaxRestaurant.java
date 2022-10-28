package com.covid.web.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class RelaxRestaurant {
	public int rowNum;
	public int relaxSeq;	// 안심식당 SEQ
	public String relaxZipcode;	// 시도 코드
	public String relaxSiNm;	// 시도명
	public String relaxSidoNm;	// 시군구명
	public String relaxRstrntNm;	// 사업자명
	public String relaxRstrntRepresent;	// 대표자명
	public String relaxAdd1;	// 주소1
	public String relaxAdd2;	// 주소2
	public String relaxGubun;	// 업종
	public String relaxGubunDetail;	// 업종 상세
	public String relaxRstrntTel;	// 전화번호
	public String relaxUseYn;	// 선정 여부
	public String relaxRstrntEtc;	// 비고
	public Date relaxRstrntRegDt;	// 안심식당 지정일
	public Date relaxRstrntCnclDt;	// 안심식당 취소일
	public Date relaxUpdateDt;	// 수정일
	public String x;	// x좌표
	public String y;	// y좌표
}
