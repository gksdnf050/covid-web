package com.covid.web.util;

import com.covid.web.dto.ApiResponseDto;
import com.covid.web.dto.HospitalDto;
import com.covid.web.mapper.CovidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.covid.web.util.ApiUtil.*;
import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
@PropertySource("classpath:application.properties")
public class HospitalUtil {
    @Value("${open-api.key.covid-hospital-api-key}")
    private String covidHospitalApiKey; // 애플리케이션 클라이언트 아이디값";

    @Autowired
    private CovidMapper covidMapper;

    @Autowired
    private KakaoUtil kakaoUtil;

    public ApiResponseDto getAllHospital(String pageNo, String numOfRows, String spclAdmTyCd) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(
                "http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"); /* URL */
        urlBuilder.append("?" + encode("ServiceKey") + "=" + covidHospitalApiKey); /* Service Key */
        urlBuilder.append("&" + encode("pageNo") + "=" + encode(pageNo)); /* 페이지번호 */
        urlBuilder.append("&" + encode("numOfRows") + "=" + encode(numOfRows)); /* 한 페이지 결과 수 */

        if (spclAdmTyCd != null)
            urlBuilder.append("&" + encode("spclAdmTyCd") + "=" + encode(spclAdmTyCd)); /* A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관 */

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        ApiResponseDto apiResponseDto = getResultByResponse(conn);

        String jsonString = xmlToJson(apiResponseDto.getResult(), true);

        apiResponseDto.setResult(jsonString);

        return apiResponseDto;
    }

    public static String filterAgencyName(String agencyName){
        String[] filterWordList = {"재단", "법인", " "};  // split을 이용하여 특수문자로 문자열을 나누는 경우에는 \\를 사용
        String[] agenCyList = {"병원", "보건소", "의원", "의학원", "의료원", "센터", "보건과", "보건지소"};

        agencyName = agencyName.replaceAll("\\(.*?\\)", "").trim(); // 괄호와 괄호 안 내용을 삭제 후, 양쪽 공백 제거
                                                                                    // .은 임의의 한 문자를 나타내며, *은 0개 이상의 문자를 나타내며, ?는 가능한 적은 패턴과 일치하도록 한다. (lazy)
                                                                                    // 만약 ?를 사용하지 않으면 예를 들어 "(ㅁㄴㅇ) (ㅂㅈㄷ)" 문자열에서 () 사이의 0개 이상의 문자와 일치하는 가장 긴 패턴인 "(ㅁㄴㅇ) (ㅂㅈㄷ)"과 일치하지만. ?을 사용하면 가장 짧은 패턴인 "(ㅁㄴㅇ)"와 "(ㅂㅈㄷ)"가 일치하게 된다.
        for(String filterElement : filterWordList){
            String[] splitedArray = agencyName.split(filterElement);

            agencyName = splitedArray[splitedArray.length - 1]; // 가장 마지막 token
        }
        return agencyName;
    }

    @Scheduled(cron = "0 0 0 * * *")  // 매일 00시에 실행 (https://javafactory.tistory.com/1386 참고)
    public void insertAllHospital() throws Exception{
        String pageNo = "1";
        String numOfRows = "1000000";
        String spclAdmTyCd = null;

        ApiResponseDto apiResponseDto = getAllHospital(pageNo, numOfRows, spclAdmTyCd);

        String responseResult = apiResponseDto.getResult();

        Map<String, Object> jsonMap = jsonToMap(responseResult);

        LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) jsonMap.get("response");
        LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) response.get("body");
        LinkedHashMap<String, Object> items = (LinkedHashMap<String, Object>) body.get("items");
        ArrayList item = (ArrayList) items.get("item");

        covidMapper.deleteAllHospital();
        covidMapper.alterHospitalAutoIncrement();

        for(Object hospital : item){
            LinkedHashMap hospitalInfo = (LinkedHashMap) hospital;

            String agencyName = (String) hospitalInfo.get("yadmNm");
            String filterdAgencyName =  filterAgencyName((String) hospitalInfo.get("yadmNm"));

            String sido = (String) hospitalInfo.get("sidoNm");
            String sggu = (String) hospitalInfo.get("sgguNm");
            String selectionType = (String) hospitalInfo.get("hospTyTpCd");
            String tel = (String) hospitalInfo.get("telno");
            String typeCode = (String) hospitalInfo.get("spclAdmTyCd");
            Date operableDate = stringToDate((String) hospitalInfo.get("adtFrDd"), "yyyyMMdd");    // api가 20202000와 같은 식으로 응답하기 때문에 StringToDate 사용.

            String x = null;
            String y = null;

            String query = sido + " " + sggu + " " + filterdAgencyName;

            ApiResponseDto searchResult = kakaoUtil.KakaolocalSearchApi(query, "1");

            Map<String, Object> jsonSearchResult = jsonToMap(searchResult.getResult());
            int total_count = (Integer) ((LinkedHashMap) jsonSearchResult.get("meta")).get("total_count");

            if(total_count != 0){
                Object documents = ((ArrayList) jsonSearchResult.get("documents")).get(0);     // 좌표 조회 첫번째 결과
                x = (String) ((LinkedHashMap)documents).get("x");
                y = (String) ((LinkedHashMap)documents).get("y");
            }

            HospitalDto hospitalDto = new HospitalDto(agencyName, sido, sggu, selectionType, tel, typeCode, x, y, operableDate);
            covidMapper.insertHospital(hospitalDto);
        }
    }
}
