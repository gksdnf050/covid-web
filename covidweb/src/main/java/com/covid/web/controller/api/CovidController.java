package com.covid.web.controller.api;

import com.covid.web.dto.HospitalDto;
import com.covid.web.dto.RestaurantDto;
import com.covid.web.mapper.CovidMapper;
import com.covid.web.util.HospitalUtil;
import com.covid.web.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CovidController {
	@Autowired
	private RestaurantUtil restaurantUtil;
	@Autowired
	private HospitalUtil hospitalUtil;

	@Autowired
	CovidMapper covidMapper;

	@GetMapping("/hospital")
	public List<HospitalDto> getAllCovidHospital() {
		return covidMapper.getHospital();
	}


	@GetMapping("/restaurant")
	public List<RestaurantDto> getCovidRestaurant() {
		return covidMapper.getRestaurant();
	}

}
