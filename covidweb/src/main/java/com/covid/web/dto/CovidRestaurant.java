package com.covid.web.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidRestaurant {
	public String restaurant;
	public String representative;
	public String zipcode;
	public String sido;
	public String sggu;
	public String category;
	public String category_detail;
	public String tel;
	public String etc;
	public String selected;
	public Date reg_date;
	public Date cancel_date;
	public Date update_date;
	public Long seq;
	public String add1;
	public String add2;
	public String x;
	public String y;
}
