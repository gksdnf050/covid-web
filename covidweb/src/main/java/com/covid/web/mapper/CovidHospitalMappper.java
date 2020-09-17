package com.covid.web.mapper;

import com.covid.web.dto.CovidHospital;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CovidHospitalMappper {
    int insertHospital(CovidHospital hospitalDto);
    int deleteAllHospital();
    int alterHospitalAutoIncrement();
    List<CovidHospital> getHospital();
}
