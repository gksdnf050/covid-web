package com.covid.web.controller.api;

import com.covid.web.model.entity.RelaxHospital;
import com.covid.web.model.entity.RelaxRestaurant;
import com.covid.web.repository.RelaxHospitalRepository;
import com.covid.web.repository.RelaxRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relax-info")
public class RelaxController {
	@Autowired
	RelaxRestaurantRepository relaxRestaurantRepository;

	@Autowired
	RelaxHospitalRepository relaxHospitalRepository;

	@GetMapping("/hospital")
	public List<RelaxHospital> getAllCovidHospitalInfo() {
		return relaxHospitalRepository.getAllHospital();
	}


	@GetMapping("/restaurant")
	public List<RelaxRestaurant> getAllCovidRestaurantInfo() {
		return relaxRestaurantRepository.getAllRestaurant();
	}
}

