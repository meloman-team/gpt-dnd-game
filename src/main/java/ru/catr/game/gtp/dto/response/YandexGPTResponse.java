package ru.catr.game.gtp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Дока <a href="https://yandex.cloud/ru/docs/ai-studio/text-generation/api-ref/TextGeneration/completion">...</a>
 * Модель ответа TextGeneration
 * Для метода генерации вариантов автозавершения текста в синхронном режиме.
 * Ответ, содержащий сгенерированные варианты завершения текста.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YandexGPTResponse {

    @JsonProperty("result")
    private Result result;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {

        /**
         * Список сгенерированных вариантов автозавершения
         */
        @JsonProperty("alternatives")
        private List<Alternative> alternatives;

        /**
         * Набор статистических данных, описывающих количество токенов контента, используемых моделью автодополнения.
         */
        @JsonProperty("usage")
        private ContentUsage usage;

        /**
         * Версия модели меняется с каждым новым релизом.
         */
        @JsonProperty("modelVersion")
        private String modelVersion;
    }


    /**
     * Представляет собой сгенерированный вариант автодополнения, включая его содержимое и статус генерации.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Alternative {
        /**
         * Сообщение с содержанием альтернативного варианта
         */
        @JsonProperty("message")
        private Message message;

        /**
         * enum (AlternativeStatus) // TODO
         *
         * Статус генерации альтернативы
         * ALTERNATIVE_STATUS_UNSPECIFIED: Неуказанный статус поколения
         * ALTERNATIVE_STATUS_PARTIAL: Частично сгенерированная альтернатива
         * ALTERNATIVE_STATUS_TRUNCATED_FINAL: Неполный окончательный вариант, полученный в результате достижения максимально допустимого количества токенов.
         * ALTERNATIVE_STATUS_FINAL: Окончательный вариант сгенерирован без превышения каких-либо ограничений.
         * ALTERNATIVE_STATUS_CONTENT_FILTER: Генерация была остановлена из-за обнаружения потенциально конфиденциальной информации в запросе или сгенерированном ответе. Для исправления измените запрос и перезапустите генерацию.
         * ALTERNATIVE_STATUS_TOOL_CALLS: Инструменты были вызваны во время генерации завершения.
         */
        @JsonProperty("status")
        private String status;
    }

    /**
     * Объект сообщения, представляющий собой оболочку для входных и выходных данных модели завершения.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Message {
        /**
         * Идентификатор отправителя сообщения. Поддерживаемые роли.
         *
         * system: Специальная роль, используемая для определения поведения модели завершения.
         * assistant: Роль, используемая моделью для генерации ответов.
         * user:Роль, используемая пользователем для описания запросов к модели.
         */
        @JsonProperty("role")
        private String role;

        /**
         * Текстовое содержимое сообщения.
         * Содержит только одно из полей: text, toolCallList или toolResultList. Содержимое сообщения.
         */
        @JsonProperty("text")
        private String text;

        /**
         * Список вызовов инструментов, выполненных моделью в процессе генерации ответа.
         * Содержит только одно из полей: text, toolCallList или toolResultList. Содержимое сообщения.
         */
        @JsonProperty("toolCallList")
        private ToolCallList toolCallList;

        /**
         * Список результатов работы внешних инструментов, вызванных моделью.
         * Содержит только одно из полей: text, toolCallList или toolResultList. Содержимое сообщения.
         */
        @JsonProperty("toolResultList")
        private ToolResultList toolResultList;
    }

    /**
     * Представляет собой список вызовов инструментов.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ToolCallList {
        /**
         * Список вызовов инструментов, которые необходимо выполнить.
         */
        @JsonProperty("toolCalls")
        private List<ToolCall> toolCalls;
    }

    /**
     * Представляет собой вызов инструмента.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ToolCall {
        /**
         * Представляет собой вызов функции.
         * Содержит только одно из полей: functionCall.
         */
        @JsonProperty("functionCall")
        private FunctionCall functionCall;
    }

    /**
     * Представляет собой вызов функции с конкретными аргументами.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FunctionCall {
        /**
         * Название вызываемой функции
         */
        @JsonProperty("name")
        private String name;

        /**
         * Структурированные аргументы, передаваемые функции.
         * Эти аргументы должны соответствовать JSON-схеме, определенной в параметрах соответствующей функции.
         */
        @JsonProperty("arguments")
        private Object arguments;
    }

    /**
     * Представляет собой список результатов работы инструмента.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ToolResultList {
        /**
         * Список результатов работы инструмента
         */
        @JsonProperty("toolResults")
        private List<ToolResult> toolResults;
    }

    /**
     * Представляет собой результат вызова инструмента.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ToolResult {
        /**
         * Представляет результат вызова функции.
         * Содержит только одно из полей: functionResult.
         */
        @JsonProperty("functionResult")
        private FunctionResult functionResult;
    }

    /**
     * Представляет собой результат вызова функции.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FunctionResult {
        /**
         * Название выполненной функции
         */
        @JsonProperty("name")
        private String name;

        /**
         * Результат вызова функции, представленный в виде строки.
         * Это поле может использоваться для хранения выходных данных функции.
         * Содержит только содержимое одного из полей.
         */
        @JsonProperty("content")
        private String content;
    }

    /**
     * Объект, представляющий количество токенов контента, используемых моделью автозавершения.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContentUsage {
        /**
         * Количество токенов в текстовой части входных данных модели.
         */
        @JsonProperty("inputTextTokens")
        private String inputTextTokens;

        /**
         * Количество токенов в сгенерированном варианте автодополнения.
         */
        @JsonProperty("completionTokens")
        private String completionTokens;

        /**
         * Общее количество токенов, включая все входные токены и все сгенерированные токены.
         */
        @JsonProperty("totalTokens")
        private String totalTokens;

        /**
         * Предоставляет дополнительную информацию о том, как использовались токены завершения.
         */
        @JsonProperty("completionTokensDetails")
        private CompletionTokensDetails completionTokensDetails;
    }

    /**
     * Предоставляет дополнительную информацию о том, как использовались токены завершения.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CompletionTokensDetails {
        /**
         * Количество токенов, используемых специально для внутренних рассуждений, выполняемых моделью.
         */
        @JsonProperty("reasoningTokens")
        private String reasoningTokens;
    }
}