package ru.catr.game.gtp.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.catr.game.gtp.config.prop.RestConfig;
import ru.catr.game.gtp.config.prop.YandexCloudConfig;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate yandexGptRestTemplate(YandexCloudConfig yandexCloudConfig, RestConfig restConfig) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(restConfig.connectTimeout());
        factory.setReadTimeout(restConfig.readTimeout());

        RestTemplate restTemplate = new RestTemplate(factory);

        // Добавляем интерсептор для авторизации
        restTemplate.setInterceptors(Collections.singletonList(
                (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Api-Key " + yandexCloudConfig.apiKey());
                    request.getHeaders().add("Content-Type", "application/json");
                    return execution.execute(request, body);
                }
        ));

        return restTemplate;
    }

}