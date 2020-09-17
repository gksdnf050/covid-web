package com.covid.web.util;

import com.covid.web.dto.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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

	public static Map<String, Object> jsonToMap(String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, Map.class);
	}

	public static Date stringToDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateStr);
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String xmlToJson(String xmlString, boolean keepStrings){
		JSONObject xmlJSONObj = XML.toJSONObject(xmlString, keepStrings);

		return xmlJSONObj.toString(4);
	}

	public static Date plusDate(Date date, int day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);

		return cal.getTime();
	}

	public static Object mapToDto(Map<String, Object> map, Class<?> classType){	// https://zorba91.tistory.com/28 참조
		final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
		return mapper.convertValue(map, classType);
	}
}
