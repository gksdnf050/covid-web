package com.covid.web.mapper.relaxInfo;

import com.covid.web.dto.relaxInfo.RelaxRestaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelaxRestaurantMapper {
    int insertInfo(RelaxRestaurant restaurantDto);
    int deleteAll();    // 테이블의 모든 row 지움
    int initializeAutoIncrement();  // restaurant 테이블의 AUTO_INCREMENT를 1으로 초기화
    List<RelaxRestaurant> getAllRestaurant();   // 모든 row 조회 (x 또는 y죄표가 null이 아닌)
}
