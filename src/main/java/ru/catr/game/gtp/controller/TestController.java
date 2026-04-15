package ru.catr.game.gtp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.catr.game.gtp.service.GPTService;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController implements ITestController {

    @Qualifier(value = "olamaGPTService")
    private GPTService gptService;

    @GetMapping("/send-to-ai/{text}")
    public ResponseEntity<String> sendToAI(
            @PathVariable String text) {
        String responseText = gptService.generateText(text, "");
        return ResponseEntity.ok(responseText);
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Service is UP");
    }
}