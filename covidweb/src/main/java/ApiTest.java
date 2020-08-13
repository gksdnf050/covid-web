import com.covid.web.dto.ApiResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.covid.web.util.ApiUtil.getResultByResponse;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

public class ApiTest {
    public static void testHospital() throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + "KoHKDfRy7ikARry4tmUsUR40977GjgeRnIE6kdRYjsiueTVkBtpETT0ZKlqHYUH%2BPDUKt%2B4I8uym%2FO3LvuAbZA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + encode("1")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + encode("10")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("spclAdmTyCd","UTF-8") + "=" + encode("A0")); /*A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관*/

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        ApiResponseDto apiResponseDto = getResultByResponse(conn);
        System.out.println("ResponseCode" + apiResponseDto.getCode());
        System.out.println("ResponseResult" + apiResponseDto.getResult());
    }
    public static void main(String[] args) throws IOException {
        testHospital();
    }
}