package com.covid.web.util;

import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.covid.web.dto.ApiResponseDto;

@Component
public class RestaurantUtil {

	@Autowired
    private ApiUtil apiUtil;
	
	
	 public JSONArray()
	 public void getCovidRestaurant() throws Exception { 
		 	int startIndex = 1, endIndex = 1000;
		 	
		 	
	        ApiResponseDto apiResponseDto = apiUtil.covidRestaurant("1", "2");
	        int responseCode = apiResponseDto.getCode();
	        String responseResult = apiResponseDto.getResult();
	        
	        JSONParser parser = new JSONParser();
	        Object obj = parser.parse( responseResult );
	        JSONObject jsonObj = (JSONObject) obj;
	        JSONObject jsonObj2 =(JSONObject) jsonObj.get("Grid_20200713000000000605_1");
	        JSONArray parse_itemlist = (JSONArray) jsonObj2.get("row");
	        JSONObject imsi = (JSONObject) parse_itemlist.get(0);

	        String name = (String) imsi.get("RELAX_SI_NM");
	        
	        System.out.println(name);
	       // Map temp = (Map) apiUtil.parseStringToMap(responseResult).get("Grid_20200713000000000605_1");
	        //JsonArray arr =  (JsonArray) temp.get("row");
	        //System.out.println(temp.get("row"));
	       // Map temp2 = apiUtil.parseStringToMap(list.get(0));
	       // System.out.println(list.get(0)); 
	        //System.out.println(temp2.get("RELAX_ZIPCODE"));
	       /* String RELAX_ZIPCODE = list.get("RELAX_ZIPCODE");
	        String RELAX_SI_NM
	        String RELAX_SIDO_NM
	        String RELAX_RSTRNT_NM
	        String RELAX_RSTRNT_REPRESENT
	        String RELAX_RSTRNT_REG_DT
	        String RELAX_ADD1
	        String RELAX_ADD2
	        String RELAX_GUBUN
	        String RELAX_GUBUN_DETAIL
	        String RELAX_RSTRNT_TEL
	        String RELAX_RSTRNT_ETC
	        String RELAX_USE_YN
	        String RELAX_RSTRNT_CNCL_DT
	        String RELAX_UPDATE_DT
	        String RELAX_SEQ
	        */
	        
	        
	        
	        
	    }
}
