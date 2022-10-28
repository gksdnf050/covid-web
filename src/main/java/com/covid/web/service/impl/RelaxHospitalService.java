package com.covid.web.service.impl;

import com.covid.web.model.entity.RelaxHospital;
import com.covid.web.repository.RelaxHospitalRepository;
import com.covid.web.service.CovidInfoService;
import com.covid.web.util.KakaoMapUtil;
import com.covid.web.util.relaxInfo.RelaxHospitalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static com.covid.web.util.ApiUtil.getItemFromXmlResponse;
import static com.covid.web.util.ApiUtil.mapToDto;

@Service
public class RelaxHospitalService implements CovidInfoService {
    @Autowired
    private RelaxHospitalRepository hospitalMappper;

    @Autowired
    private KakaoMapUtil kakaoMapUtil;

    @Autowired
    private RelaxHospitalUtil hospitalUtil;

    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0 0 * * *")  // 매일 00시에 실행 (https://javafactory.tistory.com/1386 참고)
    public int insertTodayInfo() throws IOException {   // 안심병원 api로부터 안심병원 정보를 받아 DB에 삽입하는 메서드
        String pageNo = "1";    // 페이지 번호
        String numOfRows = "1000000";   // 한 페이지 결과 수
        String spclAdmTyCd = null;  // 구분코드

        int insertCount = 0;

        URL uri = hospitalUtil.makeHospitalApiUrl(pageNo, numOfRows, spclAdmTyCd);   // url에 query parameter을 붙여 uri를 만듦.
        ArrayList<Object> items = (ArrayList<Object>) getItemFromXmlResponse(uri);  // 해당 uri로 요청한 결과에서 items 항목을 추출

        if(items != null){  // 안심병원 정보를 얻은 경우
            hospitalMappper.deleteAll();    //  현재 DB에 저장된 안심병원 정보를 모두 삭제
            hospitalMappper.initializeAutoIncrement();  // 증가된 AUTO_INCREMENT를 1으로 초기화

            for(Object item : items){   // 각 item을 순회
                RelaxHospital hospital = (RelaxHospital)mapToDto((Map<String, Object>)item, RelaxHospital.class);   // map을 dto로 변환

                String filteredHospitalName =  hospitalUtil.filterHospitalName(hospital.getYadmNm());    // 병원명에서 불필요한 문자열을 제거

                String query = hospital.getSidoNm() + " " + hospital.getSgguNm() + " " + filteredHospitalName;  // 주소를 좌표로 변환하기 위해 시도명, 시군구명, 병원명을 이어 붙여 query를 만듦.

                Map<String, String> pointMap = kakaoMapUtil.addressToPoint(query); // "시도명  시군구명  병원명"을 좌표로 변환

                hospital.setX(pointMap.get("x"));   // 좌표 변환 결과를 dto에 set (좌표 변환에 실패하면 null이 저장됨)
                hospital.setY(pointMap.get("y"));

                insertCount += hospitalMappper.insertInfo(hospital);   // 안심병원 정보를 db에 삽입
            }
        }

        return insertCount;
    }
}
