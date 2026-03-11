package ru.catr.game.gtp.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Дока <a href="https://yandex.cloud/ru/docs/ai-studio/text-generation/api-ref/TextGeneration/completion">...</a>
 * Модель запроса TextGeneration
 * Для метода генерации вариантов автозавершения текста в синхронном режиме.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YandexGPTRequest {
    /**
     * Идентификатор модели, которая будет использоваться для генерации автодополнения.
     */
    @JsonProperty("modelUri")
    private String modelUri;

    /**
     * Параметры конфигурации для генерации автозавершения
     */
    @JsonProperty("completionOptions")
    private CompletionOptions completionOptions;

    /**
     * Список сообщений, представляющих контекст для модели завершения.
     */
    @JsonProperty("messages")
    private List<Message> messages;

    /**
     * Список инструментов, которые модель может использовать при генерации завершения.
     * Примечание: Этот параметр пока не поддерживается и будет проигнорирован, если он указан.
     */
    @JsonProperty("tools")
    private List<Tool> tools;

    /**
     * Если установить значение true, модель ответит действительным JSON-объектом. Обязательно явно запросите у модели JSON.
     * В противном случае она может генерировать избыточное количество пробелов и работать бесконечно, пока не достигнет лимита токенов.
     * Содержит только одно из полей: jsonObject и jsonSchema.
     * Задает формат ответа модели.
     */
    @JsonProperty("jsonObject")
    private Boolean jsonObject;

    /**
     * Обеспечивает использование определенной структуры JSON для ответа модели на основе предоставленной схемы.
     * Содержит только одно из полей: jsonObject и jsonSchema.
     * Задает формат ответа модели.
     */
    @JsonProperty("jsonSchema")
    private JsonSchema jsonSchema;

    /**
     * Определяет, может ли модель генерировать несколько вызовов инструментов в одном ответе. По умолчанию — true.
     */
    @JsonProperty("parallelToolCalls")
    private Boolean parallelToolCalls;

    /**
     * Определяет, как модель должна выбирать, какой инструмент (или инструменты) использовать при генерации ответа.
     */
    @JsonProperty("toolChoice")
    private ToolChoice toolChoice;

    /**
     * Определяет параметры генерации автозавершения.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CompletionOptions {
        /**
         * Обеспечивает потоковую передачу частично сгенерированного текста.
         */
        @JsonProperty("stream")
        private Boolean stream;

        /**
         * Влияет на креативность и случайность ответов. Должно быть числом от 0 (включительно) до 1 (включительно).
         * Более низкие значения приводят к более прямолинейным ответам, а более высокие — к большей креативности и случайности.
         * Температура по умолчанию: 0,3
         */
        @JsonProperty("temperature")
        private Double temperature;

        /**
         * Ограничение на количество токенов, используемых для генерации однократного завершения. Должно быть больше нуля.
         * Это максимально допустимое значение параметра может зависеть от используемой модели.
         */
        @JsonProperty("maxTokens")
        private String maxTokens;

        /**
         * Настраивает возможности модели по рассуждениям, позволяя ей выполнять внутренние рассуждения перед тем, как дать ответ.
         */
        @JsonProperty("reasoningOptions")
        private ReasoningOptions reasoningOptions;

        /**
         * Представляет собой варианты рассуждений, позволяющие модели выполнять внутренние рассуждения перед генерацией ответа.
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ReasoningOptions {
            /**
             * enum (ReasoningMode) // TODO заменить на енум
             * Указывает используемый режим рассуждений.
             *
             * REASONING_MODE_UNSPECIFIED: Неуказанный режим рассуждений
             * DISABLED: Отключает логический анализ. Модель сгенерирует ответ, не выполняя никаких внутренних рассуждений.
             * ENABLED_HIDDEN: Позволяет осуществлять логическое рассуждение скрытым образом, не раскрывая пользователю этапы этого процесса.
             */
            @JsonProperty("mode")
            private String mode;
        }
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
         * user: Роль, используемая пользователем для описания запросов к модели.
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
                 * Представляет собой вызов функции. Содержит только одно из полей: functionCall.
                 */
                @JsonProperty("functionCall")
                private FunctionCall functionCall;

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
                    private Map<String, Object> arguments;
                }
            }
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
                 * Представляет результат вызова функции. Содержит только одно из полей: functionResult.
                 */
                @JsonProperty("functionResult")
                private FunctionResult functionResult;

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
            }
        }
    }

    /**
     * Представляет собой инструмент, который можно вызвать во время генерации автодополнения.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Tool {
        /**
         * Представляет собой функцию, которую можно вызвать. Включает только одну из функций поля.
         */
        @JsonProperty("function")
        private FunctionTool function;

        /**
         * Представляет собой функциональный инструмент, который можно вызвать во время генерации автодополнения.
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class FunctionTool {
            /**
             * Название функции
             */
            @JsonProperty("name")
            private String name;

            /**
             * Описание назначения или поведения функции.
             */
            @JsonProperty("description")
            private String description;

            /**
             * JSON-схема, определяющая ожидаемые параметры функции.
             * Схема должна описывать обязательные поля, их типы, а также любые ограничения или значения по умолчанию.
             */
            @JsonProperty("parameters")
            private Object parameters;

            /**
             * Обеспечивает строгое соблюдение схемы функции, гарантируя использование только определенных параметров.
             */
            @JsonProperty("strict")
            private Boolean strict;
        }
    }

    /**
     * Представляет ожидаемую структуру ответа модели с использованием JSON-схемы.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class JsonSchema {
        /**
         * JSON-схема, которой должны соответствовать выходные данные модели.
         */
        @JsonProperty("schema")
        private Object schema;
    }

    /**
     * Определяет, как модель должна выбирать, какой инструмент (или инструменты) использовать при генерации ответа.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ToolChoice {
        /**
         * enum (ToolChoiceMode)
         * Задает общий режим вызова инструмента.
         * Включает только одно из полей: mode и functionName.
         *
         * TOOL_CHOICE_MODE_UNSPECIFIED: Сервер выберет поведение по умолчанию, которое является AUTO.
         * NONE: Модель не будет вызывать ни один инструмент и сгенерирует стандартный текстовый ответ.
         * AUTO: Модель может выбрать между генерацией текстового ответа или вызовом одного или нескольких инструментов. Это поведение по умолчанию.
         * REQUIRED: Модель обязана вызывать один или несколько инструментов.
         */
        @JsonProperty("mode")
        private String mode;

        /**
         * Принуждает модель вызывать определенную функцию. Предоставленная строка должна совпадать с именем функции в API-запросе.
         * Включает только одно из полей: mode и functionName.
         */
        @JsonProperty("functionName")
        private String functionName;
    }
}