package ru.catr.game.gtp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Тестовый контроллер", description = "Минимальный контроллер для тестирования")
public interface ITestController {

    @Operation(summary = "Приветствие", description = "Возвращает приветственное сообщение")
    @ApiResponse(responseCode = "200", description = "Успешный ответ")
    ResponseEntity<String> hello();

    @Operation(summary = "Персонализированное приветствие",
            description = "Возвращает приветствие с указанным именем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "400", description = "Неверный параметр")
    })
    ResponseEntity<String> greet(
            @Parameter(description = "Имя пользователя", required = true, example = "John")
            String name);

    @Operation(summary = "Эхо-запрос", description = "Возвращает отправленные данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно возвращены"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    ResponseEntity<String> echo(
            @Parameter(description = "Текст для эхо", required = true, example = "Test message")
            String message);

    @Operation(summary = "Проверка статуса", description = "Проверяет работоспособность API")
    @ApiResponse(responseCode = "200", description = "Сервис работает")
    ResponseEntity<String> checkStatus();
}
