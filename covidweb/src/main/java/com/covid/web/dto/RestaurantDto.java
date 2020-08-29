package com.covid.web.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
	String restaurant;
	String representative; 
	String zipcode; 
	String sido; 
	String sggu; 
	String category; 
	String category_detail; 
	String tel; 
	String etc; 
	String selected; 
	Date reg_date; 
	Date cancel_date; 
	Date update_date; 
	Long seq; 
	String add1; 
	String add2;
	String x; 
	String y;
}
