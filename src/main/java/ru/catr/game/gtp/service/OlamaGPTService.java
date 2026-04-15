package ru.catr.game.gtp.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.catr.game.gtp.config.prop.OllamaConfig;
import ru.catr.game.gtp.dto.request.OllamaChatRequest;
import ru.catr.game.gtp.dto.response.OllamaChatResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "gpt.provider", havingValue = "ollama", matchIfMissing = true)
public class OlamaGPTService implements GPTService {

    private final RestClient olamaGptRestClient;
    private final OllamaConfig config;

    public String generateText(String prompt, String context) {
        try {
            OllamaChatRequest request = createRequest(prompt, context);
            OllamaChatResponse response = olamaGptRestClient.post()
                    .uri("http://localhost:11434/api/chat")
                    .body(request)
                    .retrieve()
                    .body(OllamaChatResponse.class);
            return extractResponseText(response);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обращении к YandexGPT", e);
        }
    }

    private OllamaChatRequest createRequest(String prompt, String context) {
        String model = config.model();

        List<OllamaChatRequest.Message> messages = new ArrayList<>();
        messages.add(OllamaChatRequest.Message.builder()
                .role("system")
                .content("Ты мастер текстовой приключенческой игры. " +
                        "Отвечай кратко, создавай атмосферу. " + context)
                .build());
        messages.add(OllamaChatRequest.Message.builder()
                .role("user")
                .content(prompt)
                .build());

        OllamaChatRequest request = new OllamaChatRequest();
        request.setModel(model);
        request.setMessages(messages);
        request.setStream(false); // отключаем потоковую передачу, чтобы не заморачиваться с MediaType.parseMediaType("application/x-ndjson")

        return request;
    }

    private String extractResponseText(OllamaChatResponse response) {
        return response.getMessage().getContent();
    }
}