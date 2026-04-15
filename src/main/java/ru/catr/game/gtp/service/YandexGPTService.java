package ru.catr.game.gtp.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.catr.game.gtp.config.prop.YandexCloudConfig;
import ru.catr.game.gtp.dto.request.YandexGPTRequest;
import ru.catr.game.gtp.dto.response.YandexGPTResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "gpt.provider", havingValue = "yandex")
public class YandexGPTService implements GPTService{

    private final RestClient yandexGptRestClient;
    private final YandexCloudConfig yandexCloudConfig;

    public String generateText(String prompt, String context) {
        try {
            YandexGPTResponse response = yandexGptRestClient.post()
                    .uri(yandexCloudConfig.apiUrl())
                    .body(createRequest(prompt, context))
                    .retrieve()
                    .body(YandexGPTResponse.class);
            return extractResponseText(response);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обращении к YandexGPT", e);
        }
    }

    private YandexGPTRequest createRequest(String prompt, String context) {
        String folderId = yandexCloudConfig.folderId();
        String model = yandexCloudConfig.model();
        String modelVersion = yandexCloudConfig.modelVersion();

        List<YandexGPTRequest.Message> messages = new ArrayList<>();
        messages.add(YandexGPTRequest.Message.builder()
                .role("system")
                .text("Ты мастер текстовой приключенческой игры. " +
                        "Отвечай кратко, создавай атмосферу. " + context)
                .build());
        messages.add(YandexGPTRequest.Message.builder()
                .role("user")
                .text(prompt)
                .build());

        YandexGPTRequest request = new YandexGPTRequest();
        request.setModelUri(String.format("gpt://%s/%s/%s", folderId, model, modelVersion)); // gpt://folderId/yandexgpt-lite/latest
        request.setMessages(messages);

        return request;
    }

    private String extractResponseText(YandexGPTResponse response) {
        return response.getResult().getAlternatives().getFirst().getMessage().getText();
    }
}