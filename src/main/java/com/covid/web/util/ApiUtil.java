package com.covid.web.util;

import com.covid.web.model.transfer.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.CaseUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApiUtil {
	public static ApiResponse getResultByResponse(HttpURLConnection conn) throws IOException {
		BufferedReader bufferedReader;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}

		bufferedReader.close();
		conn.disconnect();

		int code = conn.getResponseCode();
		String result = stringBuffer.toString();

		return new ApiResponse(code, result);
	}

	public static Object getItemFromXmlResponse(URL url) throws IOException {	// API로부터 xml 형식으로 응답받는 경우 response에서 item를 추출하여 반환
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		ApiResponse apiResponse = getResultByResponse(conn);	// API 요청

		String jsonString = xmlToJson(apiResponse.getResult(), true);	// XML을 JSON으로 변환

		Map<String, Object> jsonMap = jsonToMap(jsonString);	// Json을 Map으로 변환

		try{
			LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) jsonMap.get("response");
			LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.get("body");
			LinkedHashMap<String, Object> items = (LinkedHashMap<String, Object>) body.get("items");

			return items.get("item");
		}catch(Exception e){
			return null;
		}
	}

	public static Map<String, Object> convertMapKeyToCamelCase(Map<String, Object> map){	// Map의 Key를 camelCase로 변환
		Map<String, Object> resultMap = new HashMap<>();	// 변환 결과 map

		for(Map.Entry<String, Object> entry : map.entrySet()){	// 인자로 전달받은 map을 순회
			String camelCaseKey = CaseUtils.toCamelCase(entry.getKey(), false, '_');	// key를 camelCase로 변환
			Object value = entry.getValue();	// Map의 value

			resultMap.put(camelCaseKey, value);	// 결과 맵에 put
		}

		return resultMap;
	}

	public static Map<String, Object> jsonToMap(String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, Map.class);
	}

	public static String xmlToJson(String xmlString, boolean keepStrings){
		JSONObject xmlJSONObj = XML.toJSONObject(xmlString, keepStrings);
		return xmlJSONObj.toString(4);
	}

	public static Object mapToDto(Map<String, Object> map, Class<?> classType){	// https://zorba91.tistory.com/28 참조
		final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
		return mapper.convertValue(map, classType);
	}

	public static Date stringToDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateStr);
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date plusDate(Date date, int day){
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		cal.add(Calendar.DATE, day);	// 현재 날짜에 day 만큼을 더함.

		return cal.getTime();
	}
}
