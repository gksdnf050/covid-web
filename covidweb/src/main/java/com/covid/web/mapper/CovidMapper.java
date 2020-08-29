package com.covid.web.mapper;

import com.covid.web.dto.HospitalDto;
import com.covid.web.dto.RestaurantDto;

import java.util.List;

public interface CovidMapper {
	int insertRestaurant(RestaurantDto restaurantDto);
	int deleteAllRestaurant();
	int alterRestaurantAutoIncrement();
	List<RestaurantDto> getRestaurant();

	int insertHospital(HospitalDto hospitalDto);
	int deleteAllHospital();
	int alterHospitalAutoIncrement();
	List<HospitalDto> getHospital();
}
