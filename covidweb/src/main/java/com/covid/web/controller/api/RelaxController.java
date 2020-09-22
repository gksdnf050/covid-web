package com.covid.web.controller.api;

import com.covid.web.dto.relaxInfo.RelaxHospital;
import com.covid.web.dto.relaxInfo.RelaxRestaurant;
import com.covid.web.mapper.relaxInfo.RelaxHospitalMappper;
import com.covid.web.mapper.relaxInfo.RelaxRestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relax-info")
public class RelaxController {
	@Autowired
	RelaxRestaurantMapper restaurantMapper;

	@Autowired
	RelaxHospitalMappper hospitalMappper;

	@GetMapping("/hospital")
	public List<RelaxHospital> getAllCovidHospitalInfo() {
		return hospitalMappper.getAllHospital();
	}


	@GetMapping("/restaurant")
	public List<RelaxRestaurant> getAllCovidRestaurantInfo() {
		return restaurantMapper.getAllRestaurant();
	}
}

