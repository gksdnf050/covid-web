package com.covid.web.util;

import com.covid.web.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static com.covid.web.util.ApiUtil.jsonToMap;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
public class KakaoMapUtil {
    @Value("${open-api.key.kakao-rest-api-key}")
    private String kakaoRestApiKey;

    public ApiResponse searchRecommend(String address, String size) throws IOException {
        StringBuilder apiURL = new StringBuilder("https://dapi.kakao.com/v2/local/search/keyword.json");
        apiURL.append("?size=" + size);
        apiURL.append("&query=" + encode(address));

        URL url = new URL(apiURL.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Authorization", kakaoRestApiKey);

        return getResultByResponse(conn);
    }

    public Map<String, String> addressToPoint(String address) throws IOException{ // 카카오맵 API에 요청하여 address를 좌표로 변환
        Map<String, String> pointMap = new HashMap<>();

        ApiResponse searchResult = searchRecommend(address, "1");
        Map<String, Object> jsonSearchResult = jsonToMap(searchResult.getResult());
        int total_count = (Integer) ((LinkedHashMap) jsonSearchResult.get("meta")).get("total_count");

        String x = null, y = null;

        if(total_count != 0){
            Object documents = ((ArrayList) jsonSearchResult.get("documents")).get(0);     // 좌표 조회 첫번째 결과
            x = (String) ((LinkedHashMap)documents).get("x");
            y = (String) ((LinkedHashMap)documents).get("y");
        }

        pointMap.put("x", x);   // 좌표 변환에 성공하면 좌표를, 실패하면 null을 put
        pointMap.put("y", y);

        return pointMap;
    }
}
