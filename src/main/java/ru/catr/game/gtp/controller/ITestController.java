package ru.catr.game.gtp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Тестовый контроллер", description = "Минимальный контроллер для тестирования")
public interface ITestController {

    @Operation(summary = "Отправка сообщения ИИ", description = "Отправляет текст ИИ и возвращает ответ")
    @ApiResponse(responseCode = "200", description = "Успешный ответ")
    ResponseEntity<String> sendToAI(
            @Parameter(description = "Текст для отправки ИИ", required = true, example = "Привет")
            String text);

    @Operation(summary = "Проверка статуса", description = "Проверяет работоспособность API")
    @ApiResponse(responseCode = "200", description = "Сервис работает")
    ResponseEntity<String> checkStatus();
}
