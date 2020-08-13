package com.covid.web.util;

import com.covid.web.dto.ApiResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ApiUtil {
    public static ApiResponseDto getResultByResponse(HttpURLConnection conn) throws IOException {
        BufferedReader bufferedReader;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
}
