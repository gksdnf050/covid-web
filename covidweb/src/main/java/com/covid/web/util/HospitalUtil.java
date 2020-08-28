package com.covid.web.util;

import com.covid.web.dto.ApiResponseDto;
import org.json.JSONObject;
import org.json.XML;
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
public class HospitalUtil {
    @Value("${open-api.key.covid-hospital-api-key}")
    private String covidHospitalApiKey; // 애플리케이션 클라이언트 아이디값";

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
}
