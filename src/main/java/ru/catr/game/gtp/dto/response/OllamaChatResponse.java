package ru.catr.game.gtp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO ответа от Ollama API на запрос /api/chat
 * Документация: <a href="https://docs.ollama.com/api/chat">link</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OllamaChatResponse {
    /**
     * Модель, которая сгенерировала ответ (например, "llama3.2:latest")
     */
    private String model;

    /**
     * Дата и время создания ответа в формате RFC3339 (например, "2024-01-15T12:34:56.789Z")
     */
    private String createdAt;

    /**
     * Сообщение от модели (содержит роль и контент)
     */
    private Message message;

    /**
     * Флаг, указывающий, завершён ли поток ответов (true - последний чанк)
     */
    private Boolean done;

    /**
     * Причина завершения генерации (например, "stop" - естественное окончание,
     * "length" - достигнут лимит токенов, "load" - принудительная остановка)
     * Поле присутствует только при done = true
     */
    private String doneReason;

    /**
     * Общее время генерации ответа в наносекундах (только при done = true)
     */
    private Long totalDuration;

    /**
     * Время загрузки модели в наносекундах (только при done = true)
     */
    private Long loadDuration;

    /**
     * Количество токенов в промпте (входном сообщении) (только при done = true)
     */
    private Integer promptEvalCount;

    /**
     * Время оценки промпта в наносекундах (только при done = true)
     */
    private Long promptEvalDuration;

    /**
     * Количество сгенерированных токенов в ответе (только при done = true)
     */
    private Integer evalCount;

    /**
     * Время генерации ответа в наносекундах (только при done = true)
     */
    private Long evalDuration;

    /**
     * Вложенный класс, представляющий одно сообщение в диалоге
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        /**
         * Роль отправителя сообщения ("user", "assistant" или "system")
         */
        private String role;

        /**
         * Текстовое содержимое сообщения
         */
        private String content;

        /**
         * Список изображений в формате base64 (опционально, для мультимодальных моделей)
         */
        private List<String> images;
    }
}
