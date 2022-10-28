package com.covid.web.util.relaxInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

import static org.yaml.snakeyaml.util.UriEncoder.encode;

@Component
public class RelaxHospitalUtil {
    @Value("${open-api.key.public-data-api-key}")
    private String publicDataApiKey; // 애플리케이션 클라이언트 아이디값";

    public URL makeHospitalApiUrl(String pageNo, String numOfRows, String spclAdmTyCd) throws IOException{  // url의 파라미터를 전달받아 안심병원 api의 요청 url을 만드는 메서드
        String url = "http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList";

        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + encode("ServiceKey") + "=" + publicDataApiKey); // Service Key
        urlBuilder.append("&" + encode("pageNo") + "=" + encode(pageNo)); // 페이지 번호
        urlBuilder.append("&" + encode("numOfRows") + "=" + encode(numOfRows)); // 한 페이지 결과 수

        if (spclAdmTyCd != null)
            urlBuilder.append("&" + encode("spclAdmTyCd") + "=" + encode(spclAdmTyCd)); /* A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관 */

        return new URL(urlBuilder.toString());
    }

    public String filterHospitalName(String hospitalName){   // 인자로 전달받은 병원명에서 불필요한 문자열을 제거하는 메서드
        String[] filterWordList = {"재단", "법인", " "};  // split을 이용하여 특수문자로 문자열을 나누는 경우에는 \\를 사용
        String[] availList = {"병원", "보건소", "의원", "의학원", "의료원", "센터", "보건과", "보건지소"};

        hospitalName = hospitalName.replaceAll("\\(.*?\\)", "").trim(); // 괄호와 괄호 안 내용을 삭제 후, 양쪽 공백 제거
                                                                                        // .은 임의의 한 문자를 나타내며, *은 0개 이상의 문자를 나타내며, ?는 가능한 적은 패턴과 일치하도록 한다. (lazy)
                                                                                        // 만약 ?를 사용하지 않으면 예를 들어 "(ㅁㄴㅇ) (ㅂㅈㄷ)" 문자열에서 () 사이의 0개 이상의 문자와 일치하는 가장 긴 패턴인 "(ㅁㄴㅇ) (ㅂㅈㄷ)"과 일치하지만. ?을 사용하면 가장 짧은 패턴인 "(ㅁㄴㅇ)"와 "(ㅂㅈㄷ)"가 일치하게 된다.
        for(String filterElement : filterWordList){ // filterWordList의 요소들로 split 한 후, 마지막 token만 남김.
            String[] splitedArray = hospitalName.split(filterElement);

            hospitalName = splitedArray[splitedArray.length - 1]; // 가장 마지막 token 선택
        }
        return hospitalName;
    }
}
