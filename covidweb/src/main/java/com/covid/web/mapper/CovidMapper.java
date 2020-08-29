package com.covid.web.mapper;

import java.util.List;

import com.covid.web.dto.RestaurantDto;

public interface CovidMapper {

	int insertRestaurant(RestaurantDto restaurantDto);
	List<RestaurantDto> getRestaurant();
}
