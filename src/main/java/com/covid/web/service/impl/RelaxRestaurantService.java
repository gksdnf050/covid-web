package com.covid.web.service.impl;

import com.covid.web.model.entity.RelaxRestaurant;
import com.covid.web.mapper.relaxInfo.RelaxRestaurantMapper;
import com.covid.web.service.CovidInfoService;
import com.covid.web.util.KakaoMapUtil;
import com.covid.web.util.relaxInfo.RelaxRestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.covid.web.util.ApiUtil.convertMapKeyToCamelCase;
import static com.covid.web.util.ApiUtil.mapToDto;

@Service
public class RelaxRestaurantService implements CovidInfoService {
    @Autowired
    private KakaoMapUtil kakaoMapUtil;

    @Autowired
    RelaxRestaurantMapper restaurantMapper;

    @Autowired
    RelaxRestaurantUtil restaurantUtil;

    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0 0 * * *")	// 매일 00시에 실행
    public int insertTodayInfo() throws IOException {
        int insertCount = 0;
        int requestSize = 1000;	// 요청 정보 개수
        int startIndex = 1;	// 요청 시작 위치
        int endIndex = requestSize;	// 요청 종료 위치
        boolean isEnd = false;	// 모든 안심식당 정보를 받아왔는지 나타내는 boolean

        restaurantMapper.deleteAll();	// restaurant 테이블의 모든 row 삭제
        restaurantMapper.initializeAutoIncrement();	// restaurant의 AUTO_INCREMENT 1으로 초기화

        while (!isEnd) {
            ArrayList<Object> items = (ArrayList<Object>) restaurantUtil.getAllRestaurant(startIndex, endIndex);	// startIndex, endIndex 사이의 안심식당 정보를 받아옴

            if(items != null){	// 안심식당 정보를 받아오는데 성공한 경우
                for (Object item : items) {	// 안심식당 순회
                    Map<String, Object> convertedMap = convertMapKeyToCamelCase((Map<String, Object>) item);	// 안심식당 정보의 property가 upper snake case로 되어 있어 camelCase로 바꾼 뒤 CovidRestayrant 클래스로 변환
                    RelaxRestaurant restaurant = (RelaxRestaurant) mapToDto(convertedMap, RelaxRestaurant.class);	// 안심식당 정보를 dto로 변환


                    String query = restaurant.getRelaxSiNm() + " " + restaurant.getRelaxSidoNm() + " " + restaurant.getRelaxRstrntNm();	// 주소를 좌표로 변환하기 위해 시도명, 시군구명, 사업자명을 이어 붙여 query를 만듦.
                    Map<String, String> pointMap = kakaoMapUtil.addressToPoint(query);	// "시도명  시군구명  사업자명"을 좌표로 변환

                    restaurant.setX(pointMap.get("x"));   // 좌표 변환 결과를 dto에 set (좌표 변환에 실패하면 null이 저장됨)
                    restaurant.setY(pointMap.get("y"));

                    insertCount += restaurantMapper.insertInfo(restaurant); // 안심식당 정보를 db에 삽입
                }

                startIndex += requestSize;
                endIndex += requestSize;
                if (items.size() < requestSize)	// 실제 받아온 정보의 개수가 요청 개수 보다 적을 때는 더이상 받아올 정보가 없음을 나타내므로 반복문을 벗어남
                    isEnd = true;
            }
        }

        return insertCount;
    }
}
