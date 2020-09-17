package com.covid.web.util;

import com.covid.web.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class KakaoUtil {
    @Value("${open-api.key.kakao-rest-api-key}")
    private String kakaoRestApiKey;

    public ApiResponse KakaolocalSearchApi(String query, String size) throws IOException {
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
}
