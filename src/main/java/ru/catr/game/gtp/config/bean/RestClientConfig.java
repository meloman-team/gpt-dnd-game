package ru.catr.game.gtp.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.catr.game.gtp.config.prop.RestConfig;
import ru.catr.game.gtp.config.prop.YandexCloudConfig;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient yandexGptRestClient(YandexCloudConfig yandexCloudConfig, RestConfig restConfig) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(restConfig.connectTimeout());
        factory.setReadTimeout(restConfig.readTimeout());

        return RestClient.builder()
                .requestFactory(factory)
                .defaultHeader("Authorization", "Api-Key " + yandexCloudConfig.apiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

}