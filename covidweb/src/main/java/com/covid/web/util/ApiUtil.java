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

public class ApiUtil {
	public static ApiResponseDto getResultByResponse(HttpURLConnection conn) throws IOException {
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
		ObjectMapper mapper = new ObjectMapper();
		return new ObjectMapper().readValue(jsonStr, Map.class);
	}
}
