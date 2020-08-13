import com.covid.web.dto.ApiResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.yaml.snakeyaml.util.UriEncoder.encode;

public class ApiTest {

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

    public static void testSearchLocal() throws IOException{
        String clientId = "ZtE5ipgXNtqEqTsmTiqO";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "3qAG0NeIGF";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + encode("상명대");
        URL url = new URL(apiURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("X-Naver-Client-Id", clientId);
        conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

        ApiResponseDto apiResponseDto = getResultByResponse(conn);
        System.out.println("ResponseCode" + apiResponseDto.getCode());
        System.out.println("ResponseResult" + apiResponseDto.getResult());
    }
    public void testHospital() throws IOException{
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
        testSearchLocal();
    }
}