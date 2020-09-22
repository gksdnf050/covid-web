package com.covid.web.util.relaxInfo;

import com.covid.web.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static com.covid.web.util.ApiUtil.jsonToMap;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class RelaxRestaurantUtil {

	@Value("${open-api.key.covid-restaurant-api-key}")
	private String covidRestaurantApiKey;

	public Object getAllRestaurant(int startIndex, int endIndex) throws IOException {	// 안심식당 api를 이용해 startIndex, endIndex 사이의 안심식당 정보를 받아와 반환하는 메서드
		StringBuilder urlBuilder = new StringBuilder("http://211.237.50.150:7080/openapi"); /* URL */

		urlBuilder.append("/" + encode(covidRestaurantApiKey));
		urlBuilder.append("/" + encode("json"));
		urlBuilder.append("/" + encode("Grid_20200713000000000605_1"));
		urlBuilder.append("/" + encode(Integer.toString(startIndex)));
		urlBuilder.append("/" + encode(Integer.toString(endIndex)));

		URL url = new URL(urlBuilder.toString());	// url과 query parameter를 이용해 uri를 만듦.

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		ApiResponse apiResponse = getResultByResponse(conn);	
		String responseResult = apiResponse.getResult();	// api 요청 결과

		try{
			Map<String, Object> jsonResponse = jsonToMap(responseResult);	// api 요청 결과를 map 형식으로 변환
			Map<String, Object> jsonMap = (Map<String, Object>) jsonResponse.get("Grid_20200713000000000605_1");	// Grid_20200713000000000605_1 property를 get

			return jsonMap.get("row");	// row property를 반환
		}catch (Exception e){
			return null;
		}
	}
}
