package com.covid.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid.web.util.KakaoUtil;
import com.covid.web.util.RestaurantUtil;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CovidController {
	@Autowired
	private RestaurantUtil restaurantUtil;
    @GetMapping("/hospital")
    public void getAllCovidHospital() throws IOException {

    }


    @GetMapping("/restaurant")
    public void getCovidRestaurant() throws IOException {
    	try {
			restaurantUtil.getCovidRestaurant();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
