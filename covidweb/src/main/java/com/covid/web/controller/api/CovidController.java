package com.covid.web.controller.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid.web.dto.RestaurantDto;
import com.covid.web.mapper.CovidMapper;
import com.covid.web.util.RestaurantUtil;

@RestController
@RequestMapping("/api")
public class CovidController {
	@Autowired
	private RestaurantUtil restaurantUtil;
	@Autowired
	private CovidMapper covidMapper;
    @GetMapping("/hospital")
    public void getAllCovidHospital() throws IOException {

    }


    @GetMapping("/restaurant")
    public void getCovidRestaurant() throws IOException {
    	List<RestaurantDto> dto = covidMapper.getRestaurant();
    	
    	//return dto;
    }

}
