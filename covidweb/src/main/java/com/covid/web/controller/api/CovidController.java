package com.covid.web.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CovidController {
    @GetMapping("/hospital")
    public void getAllCovidHospital() throws IOException {

    }


    @GetMapping("/restaurant")
    public void getCovidRestaurant() throws IOException {

    }

}
