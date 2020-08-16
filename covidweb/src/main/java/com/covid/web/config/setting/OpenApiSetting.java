package com.covid.web.config.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "open-api.key")
@Getter
@Setter
public class OpenApiSetting {
    private String kakaoRestApiKey;

    private String covidHospitalApiKey;
    private String covidRestaurantApiKey;

    private String naverApiClientId;
    private String naverApiClientSecret;
}
