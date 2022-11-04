package com.covid.web.config.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "open-api.key")
@Configuration
public class OpenApiSetting {
    private String kakaoRestApiKey;

    private String publicDataApiKey;
    private String covidRestaurantApiKey;

    private String naverApiClientId;
    private String naverApiClientSecret;
}
