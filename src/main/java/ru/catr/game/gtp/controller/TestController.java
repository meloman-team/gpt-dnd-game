package ru.catr.game.gtp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.catr.game.gtp.config.prop.YandexCloudConfig;
import ru.catr.game.gtp.service.YandexGPTService;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController implements ITestController {

    private YandexCloudConfig yandexCloudConfig;
    private YandexGPTService yandexGPTService;

    /**
     * Отправляет сообщение ИИ и получает ответ.
     */
    @GetMapping("/send-to-ai/{text}")
    public ResponseEntity<String> sendToAI(
            @PathVariable String text) {
        String responseText = yandexGPTService.generateText(text, "");
        return ResponseEntity.ok(responseText);
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Service is UP");
    }
}