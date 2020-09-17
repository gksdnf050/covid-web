package com.covid.web.util;

import com.covid.web.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.covid.web.util.ApiUtil.*;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class CovidInfoUtil {
    @Value("${open-api.key.public-data-api-key}")
    private String publicDataApiKey; // 애플리케이션 클라이언트 아이디값";

    public Object getCovidInfoItemByUrl(String url, String pageNo, String numOfRows, String startCreateDate, String endCreateDate) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + encode("ServiceKey") + "=" + publicDataApiKey); /*Service Key*/
        urlBuilder.append("&" + encode("pageNo") + "=" + encode(pageNo)); /*페이지번호*/
        urlBuilder.append("&" + encode("numOfRows") + "=" + encode(numOfRows)); /*한 페이지 결과 수*/
        urlBuilder.append("&" + encode("startCreateDt") + "=" + encode(startCreateDate)); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + encode("endCreateDt") + "=" + encode(endCreateDate)); /*검색할 생성일 범위의 종료*/

        URL uri = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setRequestMethod("GET");

        ApiResponse apiResponse = getResultByResponse(conn);

        String jsonString = xmlToJson(apiResponse.getResult(), true);

        Map<String, Object> jsonMap = jsonToMap(jsonString);

        try{
            LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) jsonMap.get("response");
            LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.get("body");
            LinkedHashMap<String, Object> items = (LinkedHashMap<String, Object>) body.get("items");

            return items.get("item");
        }catch(Exception e){
            return null;
        }
    }
}

