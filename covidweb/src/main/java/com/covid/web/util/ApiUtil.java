package com.covid.web.util;

import com.covid.web.dto.ApiResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class ApiUtil {
	@Value("${open-api.key.covid-restaurant-api-key}")
	private String covidRestaurantApiKey;

	@Value("${open-api.key.covid-hospital-api-key}")
	private String covidHospitalApiKey; // 애플리케이션 클라이언트 아이디값";

	@Value("${open-api.key.kakao-rest-api-key}")
	private String kakaoRestApiKey;

	public ApiResponseDto getResultByResponse(HttpURLConnection conn) throws IOException {
		BufferedReader bufferedReader;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuffer response = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			response.append(line);
		}

		bufferedReader.close();
		conn.disconnect();

		int code = conn.getResponseCode();
		String result = response.toString();

		return new ApiResponseDto(code, result);
	}

	public Map<String, Object> parseStringToMap(String jsonStr) throws JsonProcessingException {
		JSONParser parser = new JSONParser();
        Object obj = parser.parse( "결과값" );
        JSONObject jsonObj = (JSONObject) obj;
        JSONObject jsonObj2 =(JSONObject) jsonObj.get("Grid_20200713000000000605_1");
        JSONArray parse_itemlist = (JSONArray) jsonObj2.get("row");
        JSONObject imsi = (JSONObject) parse_itemlist.get(0);

		ObjectMapper mapper = new ObjectMapper();
		return new ObjectMapper().readValue(jsonStr, Map.class);
	}

	public ApiResponseDto KakaolocalSearchApi(String query, String size) throws IOException {
		StringBuilder apiURL = new StringBuilder("https://dapi.kakao.com/v2/local/search/keyword.json");
		apiURL.append("?size=" + size);
		apiURL.append("&query=" + encode(query));

		URL url = new URL(apiURL.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("Authorization", kakaoRestApiKey);

		return getResultByResponse(conn);
	}

	public ApiResponseDto covidHospital(String pageNo, String numOfRows, String spclAdmTyCd) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"); /* URL */
		urlBuilder.append("?" + encode("ServiceKey") + "=" + covidHospitalApiKey); /* Service Key */
		urlBuilder.append("&" + encode("pageNo") + "=" + encode(pageNo)); /* 페이지번호 */
		urlBuilder.append("&" + encode("numOfRows") + "=" + encode(numOfRows)); /* 한 페이지 결과 수 */

		if (spclAdmTyCd != null)
			urlBuilder.append("&" + encode("spclAdmTyCd") + "="
					+ encode(spclAdmTyCd)); /* A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관 */

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		ApiResponseDto apiResponseDto = getResultByResponse(conn);
		JSONObject xmlJSONObj = XML.toJSONObject(apiResponseDto.getResult());
		String jsonPrettyPrintString = xmlJSONObj.toString(4);

		apiResponseDto.setResult(jsonPrettyPrintString);

		return apiResponseDto;
	}

	public ApiResponseDto covidRestaurant(String startIndex, String endIndex) throws IOException {
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

		ApiResponseDto apiResponseDto = getResultByResponse(conn);

		return apiResponseDto;
	}
}
