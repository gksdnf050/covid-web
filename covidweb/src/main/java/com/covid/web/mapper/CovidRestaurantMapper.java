package com.covid.web.mapper;

import com.covid.web.dto.CovidRestaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CovidRestaurantMapper {
    int insertRestaurant(CovidRestaurant restaurantDto);
    int deleteAllRestaurant();
    int alterRestaurantAutoIncrement();
    List<CovidRestaurant> getRestaurant();
}
