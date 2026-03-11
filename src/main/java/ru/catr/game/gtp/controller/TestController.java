package ru.catr.game.gtp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String apiUrl = yandexCloudConfig.apiUrl();
        return ResponseEntity.ok("Hello from Spring Boot 4.0.2 with Java 21! apiUrl: " + apiUrl);
    }

    @GetMapping("/greet/{text}")
    public ResponseEntity<String> greet(
            @PathVariable String text) {
        String responseText = yandexGPTService.generateText(text, "");
        return ResponseEntity.ok(responseText);
    }

    @PostMapping("/echo")
    public ResponseEntity<String> echo(
            @RequestBody String message) {
        return ResponseEntity.ok("Echo: " + message);
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Service is UP");
    }
}