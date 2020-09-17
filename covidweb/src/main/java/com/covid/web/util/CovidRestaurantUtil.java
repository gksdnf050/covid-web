package com.covid.web.util;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.covid.web.mapper.CovidRestaurantMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.covid.web.dto.ApiResponse;
import com.covid.web.dto.CovidRestaurant;

@Component
@PropertySource("classpath:application.properties")
public class CovidRestaurantUtil {

	@Value("${open-api.key.covid-restaurant-api-key}")
	private String covidRestaurantApiKey;

	@Autowired
	private KakaoUtil kakaoUtil;

	@Autowired
	CovidRestaurantMapper restaurantMapper;

	@Scheduled(cron = "0 0 0 * * *")	// 매일 00시에 실행
	public void getCovidRestaurant() throws Exception {
		int requestSize = 1000;
		int startIndex = 1;
		int endIndex = 1000;
		boolean isEnd = false;

		restaurantMapper.deleteAllRestaurant();
		restaurantMapper.alterRestaurantAutoIncrement();

		while (!isEnd) {
			ApiResponse apiResponse = covidRestaurant(Integer.toString(startIndex), Integer.toString(endIndex));
			// int responseCode = apiResponseDto.getCode();
			String responseResult = apiResponse.getResult();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responseResult);
			JSONObject jsonObj = (JSONObject) obj;
			JSONObject jsonObj2 = (JSONObject) jsonObj.get("Grid_20200713000000000605_1");
			JSONArray parse_itemlist = (JSONArray) jsonObj2.get("row");

			for (int i = 0; i < parse_itemlist.size(); i++) {
				JSONObject imsi = (JSONObject) parse_itemlist.get(i);

				CovidRestaurant dto = new CovidRestaurant();
				dto.setRestaurant((String) imsi.get("RELAX_RSTRNT_NM"));
				dto.setRepresentative((String) imsi.get("RELAX_RSTRNT_REPRESENT"));
				dto.setZipcode((String) imsi.get("RELAX_ZIPCODE"));
				dto.setSido((String) imsi.get("RELAX_SI_NM"));
				dto.setSggu((String) imsi.get("RELAX_SIDO_NM"));
				dto.setCategory((String) imsi.get("RELAX_GUBUN"));
				dto.setCategory_detail((String) imsi.get("RELAX_GUBUN_DETAIL"));
				dto.setTel((String) imsi.get("RELAX_RSTRNT_TEL"));
				dto.setEtc((String) imsi.get("RELAX_RSTRNT_ETC"));
				dto.setSelected((String) imsi.get("RELAX_USE_YN"));
				dto.setReg_date((Date) imsi.get("RELAX_RSTRNT_REG_DT"));
				dto.setCancel_date((Date) imsi.get("RELAX_RSTRNT_CNCL_DT"));
				dto.setUpdate_date((Date) imsi.get("RELAX_UPDATE_DT"));
				dto.setSeq((Long) imsi.get("RELAX_SEQ"));
				dto.setAdd1((String) imsi.get("RELAX_ADD1"));
				dto.setAdd2((String) imsi.get("RELAX_ADD2"));

				String query = imsi.get("RELAX_SI_NM") + " " + imsi.get("RELAX_SIDO_NM") + " " + imsi.get("RELAX_RSTRNT_NM");
				ApiResponse apiDto = kakaoUtil.KakaolocalSearchApi(query, "1");
				String resResult = apiDto.getResult();

				Object oj = parser.parse(resResult);
				JSONObject jsoObj = (JSONObject) oj;
				JSONArray arr = (JSONArray) jsoObj.get("documents");
				if (arr.size() > 0) {
					JSONObject ob = (JSONObject) arr.get(0);

					dto.setX((String) ob.get("x"));
					dto.setY((String) ob.get("y"));
				} else {
					dto.setX(null);
					dto.setY(null);
				}

				restaurantMapper.insertRestaurant(dto);
			}

			startIndex += requestSize;
			endIndex += requestSize;
			if (parse_itemlist.size() < requestSize)
				isEnd = true;
		}

	}

	public ApiResponse covidRestaurant(String startIndex, String endIndex) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://211.237.50.150:7080/openapi"); /* URL */
		urlBuilder.append("/" + encode(covidRestaurantApiKey));
		urlBuilder.append("/" + encode("json"));
		urlBuilder.append("/" + encode("Grid_20200713000000000605_1"));
		urlBuilder.append("/" + encode(startIndex));
		urlBuilder.append("/" + encode(endIndex));

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		ApiResponse apiResponse = getResultByResponse(conn);

		return apiResponse;
	}
}
