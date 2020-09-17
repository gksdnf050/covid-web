package com.covid.web.config.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "open-api.key")
public class OpenApiSetting {
    private String kakaoRestApiKey;

    private String publicDataApiKey;
    private String covidRestaurantApiKey;

    private String naverApiClientId;
    private String naverApiClientSecret;
}
