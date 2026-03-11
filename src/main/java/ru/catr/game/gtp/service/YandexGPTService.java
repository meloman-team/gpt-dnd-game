package ru.catr.game.gtp.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.catr.game.gtp.config.prop.YandexCloudConfig;
import ru.catr.game.gtp.dto.request.YandexGPTRequest;
import ru.catr.game.gtp.dto.response.YandexGPTResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class YandexGPTService {

    private final RestTemplate yandexGptRestTemplate;
    private final YandexCloudConfig yandexCloudConfig;

    public String generateText(String prompt, String context) {
        String folderId = yandexCloudConfig.folderId();
        YandexGPTRequest request = new YandexGPTRequest();
        request.setModelUri("gpt://" + folderId + "/yandexgpt-lite/latest");

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

        request.setMessages(messages);

        HttpEntity<YandexGPTRequest> entity = new HttpEntity<>(request);

        try {
            ResponseEntity<YandexGPTResponse> response = yandexGptRestTemplate.postForEntity(
                    yandexCloudConfig.apiUrl(), entity, YandexGPTResponse.class);

            return extractResponseText(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обращении к YandexGPT", e);
        }
    }

    private String extractResponseText(YandexGPTResponse response) {
        return response.getResult().getAlternatives().getFirst().getMessage().getText();
    }
}