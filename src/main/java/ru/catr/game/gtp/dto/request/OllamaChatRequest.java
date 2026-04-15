package ru.catr.game.gtp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для взаимодействия с Ollama API endpoint /api/chat
 * Документация: <a href="https://docs.ollama.com/api/chat">link</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OllamaChatRequest {
    /**
     * Название модели для использования (например, "llama2", "mistral", "codellama")
     * Обязательное поле
     */
    @JsonProperty("model")
    private String model;

    /**
     * Список сообщений в диалоге
     * Каждое сообщение содержит роль (system/user/assistant) и содержимое
     */
    @JsonProperty("messages")
    private List<Message> messages;

    /**
     * Режим потоковой передачи ответа
     * false - ответ приходит целиком, true - ответ приходит по частям (токенами)
     * По умолчанию: false
     */
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * Дополнительные параметры модели (температура, контекст и т.д.)
     * Необязательное поле
     */
    @JsonProperty("options")
    private Options options;

    /**
     * Формат ответа модели
     * Если указать "json", модель будет возвращать только валидный JSON
     * Необязательное поле
     */
    @JsonProperty("format")
    private String format;

    /**
     * Время удержания модели в памяти после завершения запроса
     * По умолчанию: 5 минут. Укажите -1 для бесконечного удержания
     */
    @JsonProperty("keep_alive")
    private Boolean keepAlive;

    // ==================== ВЛОЖЕННЫЙ КЛАСС MESSAGE ====================

    /**
     * Сообщение в диалоге между пользователем и ассистентом
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        /**
         * Роль отправителя сообщения:
         * - "system" - системный промпт (инструкция для модели)
         * - "user" - сообщение от пользователя
         * - "assistant" - ответ модели (используется для продолжения диалога)
         */
        @JsonProperty("role")
        private String role;

        /**
         * Текстовое содержимое сообщения
         * Основной контент, который отправляется модели
         */
        @JsonProperty("content")
        private String content;

        /**
         * Список изображений в формате base64 (для мультимодальных моделей типа llava)
         * Каждое изображение должно быть закодировано в base64 строку
         */
        @JsonProperty("images")
        private List<String> images;
    }

    // ==================== ВЛОЖЕННЫЙ КЛАСС OPTIONS ====================

    /**
     * Дополнительные параметры для настройки поведения модели
     * Большинство параметров опциональны и имеют значения по умолчанию
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {

        /**
         * Количество предыдущих токенов, которые модель сохраняет в памяти
         * Влияет на контекст диалога. По умолчанию: 2048
         */
        @JsonProperty("num_keep")
        private Integer numKeep;

        /**
         * Температура генерации (0.0 - 2.0)
         * Выше = более креативные и случайные ответы
         * Ниже = более детерминированные и консервативные ответы
         * По умолчанию: 0.8
         */
        @JsonProperty("temperature")
        private Float temperature;

        /**
         * Top P (nucleus sampling) - ограничивает выборку наиболее вероятных токенов
         * Значение 0.9 означает, что выбираются токены с 90% суммарной вероятности
         * По умолчанию: 0.9
         */
        @JsonProperty("top_p")
        private Float topP;

        /**
         * Top K - ограничивает выборку K наиболее вероятных токенов
         * Например, top_k=40 означает, что модель рассматривает только 40 лучших токенов
         * По умолчанию: 40
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * Максимальное количество токенов для генерации
         * Ограничивает длину ответа модели
         * По умолчанию: 128 (зависит от модели)
         */
        @JsonProperty("num_predict")
        private Integer numPredict;

        /**
         * Штраф за повторение токенов (1.0 - 2.0)
         * Выше значение = меньше повторений
         * По умолчанию: 1.1
         */
        @JsonProperty("repeat_penalty")
        private Float repeatPenalty;

        /**
         * Случайное зерно для воспроизводимых результатов
         * Одинаковое зерно = одинаковый результат при тех же входных данных
         */
        @JsonProperty("seed")
        private Integer seed;

        /**
         * Список стоп-слов, при обнаружении которых генерация прекращается
         * Например: ["\n", "User:", "Question:"]
         */
        @JsonProperty("stop")
        private List<String> stop;

        /**
         * Параметр tail free sampling (0.0 - 1.0)
         * Альтернатива top_p, основанная на хвосте распределения
         * По умолчанию: 1.0 (отключено)
         */
        @JsonProperty("tfs_z")
        private Float tfsZ;

        /**
         * Параметр typical sampling (0.0 - 1.0)
         * Предпочитает токены с "типичной" вероятностью
         * По умолчанию: 1.0 (отключено)
         */
        @JsonProperty("typical_p")
        private Float typicalP;

        /**
         * Количество последних токенов для учета при penalize повторений
         * Влияет на параметр repeat_penalty
         * По умолчанию: 64
         */
        @JsonProperty("repeat_last_n")
        private Integer repeatLastN;

        /**
         * Штраф за присутствие токенов (от -2.0 до 2.0)
         * Положительные значения уменьшают повторение тем
         * По умолчанию: 0.0
         */
        @JsonProperty("presence_penalty")
        private Float presencePenalty;

        /**
         * Штраф за частоту токенов (от -2.0 до 2.0)
         * Уменьшает вероятность частых токенов
         * По умолчанию: 0.0
         */
        @JsonProperty("frequency_penalty")
        private Float frequencyPenalty;

        /**
         * Алгоритм mirostat для управления перплексией
         * 0 = отключен, 1 = mirostat, 2 = mirostat 2.0
         * По умолчанию: 0
         */
        @JsonProperty("mirostat")
        private Integer mirostat;

        /**
         * Целевая перплексия для mirostat (0.0 - 1.0)
         * Влияет на разнообразие текста
         * По умолчанию: 5.0
         */
        @JsonProperty("mirostat_tau")
        private Float mirostatTau;

        /**
         * Скорость обучения для mirostat (0.001 - 1.0)
         * Как быстро алгоритм адаптируется к целевой перплексии
         * По умолчанию: 0.1
         */
        @JsonProperty("mirostat_eta")
        private Float mirostatEta;

        /**
         * Размер контекстного окна (количество токенов, которое модель "помнит")
         * Больше = лучше понимает контекст, но требует больше памяти
         * По умолчанию: 2048
         */
        @JsonProperty("num_ctx")
        private Integer numCtx;

        /**
         * Размер батча для обработки токенов
         * Влияет на производительность инференса
         * По умолчанию: 512
         */
        @JsonProperty("num_batch")
        private Integer numBatch;

        /**
         * Количество GPU для использования
         * -1 = все доступные GPU, 0 = только CPU
         */
        @JsonProperty("num_gpu")
        private Integer numGpu;

        /**
         * ID основного GPU для вычислений
         * По умолчанию: 0
         */
        @JsonProperty("main_gpu")
        private Integer mainGpu;

        /**
         * Режим низкого потребления VRAM
         * true = экономия памяти за счет производительности
         * По умолчанию: false
         */
        @JsonProperty("low_vram")
        private Boolean lowVram;

        /**
         * Использовать ли кэш ключей/значений в формате float16
         * Экономит память, может немного снизить точность
         * По умолчанию: false
         */
        @JsonProperty("f16_kv")
        private Boolean f16Kv;

        /**
         * Возвращать ли логиты для всех токенов (не только предсказанных)
         * Используется для отладки и анализа
         * По умолчанию: false
         */
        @JsonProperty("logits_all")
        private Boolean logitsAll;

        /**
         * Загружать только словарь, без весов модели
         * Для тестирования, не для реального использования
         * По умолчанию: false
         */
        @JsonProperty("vocab_only")
        private Boolean vocabOnly;

        /**
         * Использовать ли memory-mapped файлы для загрузки модели
         * Ускоряет загрузку, может быть несовместимо с некоторыми ОС
         * По умолчанию: true
         */
        @JsonProperty("use_mmap")
        private Boolean useMmap;

        /**
         * Использовать ли mlock для блокировки модели в оперативной памяти
         * Предотвращает выгрузку модели в swap
         * По умолчанию: false
         */
        @JsonProperty("use_mlock")
        private Boolean useMlock;

        /**
         * Количество потоков для вычислений на CPU
         * По умолчанию: количество ядер CPU
         */
        @JsonProperty("num_thread")
        private Integer numThread;
    }

    // ==================== ВЛОЖЕННЫЙ КЛАСС ДЛЯ ОТВЕТА ====================

    /**
     * Ответ от модели Ollama
     * При stream=false приходит целиком, при stream=true приходит по частям
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatResponse {

        /**
         * Название модели, которая сгенерировала ответ
         */
        @JsonProperty("model")
        private String model;

        /**
         * Время создания ответа в формате ISO 8601
         * Пример: "2024-01-15T10:30:00.123456789Z"
         */
        @JsonProperty("created_at")
        private String createdAt;

        /**
         * Сгенерированное сообщение (содержит роль "assistant" и контент)
         * При потоковой передаче каждый чанк содержит часть сообщения
         */
        @JsonProperty("message")
        private Message message;

        /**
         * Флаг завершения генерации
         * true - генерация закончена, false - ожидаются еще чанки
         */
        @JsonProperty("done")
        private Boolean done;

        /**
         * Общее время выполнения запроса в наносекундах
         * Включает загрузку, обработку промпта и генерацию
         */
        @JsonProperty("total_duration")
        private Long totalDuration;

        /**
         * Время загрузки модели в наносекундах
         */
        @JsonProperty("load_duration")
        private Long loadDuration;

        /**
         * Количество токенов в промпте (входном сообщении)
         */
        @JsonProperty("prompt_eval_count")
        private Integer promptEvalCount;

        /**
         * Время обработки промпта в наносекундах
         */
        @JsonProperty("prompt_eval_duration")
        private Long promptEvalDuration;

        /**
         * Количество сгенерированных токенов в ответе
         */
        @JsonProperty("eval_count")
        private Integer evalCount;

        /**
         * Время генерации ответа в наносекундах
         */
        @JsonProperty("eval_duration")
        private Long evalDuration;

        /**
         * Причина завершения генерации:
         * "stop" - достигнуто стоп-слово
         * "length" - достигнут максимум токенов
         * "load" - загрузка модели
         */
        @JsonProperty("done_reason")
        private String doneReason;
    }

    // ==================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ====================

    /**
     * Быстрое создание простого запроса с одним сообщением пользователя
     * @param model название модели (например, "llama2")
     * @param userMessage текст сообщения пользователя
     * @return готовый DTO запроса
     */
    public static OllamaChatRequest createSimpleRequest(String model, String userMessage) {
        return OllamaChatRequest.builder()
                .model(model)
                .stream(false)
                .messages(List.of(
                        Message.builder()
                                .role("user")
                                .content(userMessage)
                                .build()
                ))
                .build();
    }

    /**
     * Создание запроса с системным промптом и сообщением пользователя
     * @param model название модели
     * @param systemPrompt системная инструкция для модели
     * @param userMessage сообщение пользователя
     * @return готовый DTO запроса
     */
    public static OllamaChatRequest createRequestWithSystem(
            String model,
            String systemPrompt,
            String userMessage) {
        return OllamaChatRequest.builder()
                .model(model)
                .stream(false)
                .messages(List.of(
                        Message.builder()
                                .role("system")
                                .content(systemPrompt)
                                .build(),
                        Message.builder()
                                .role("user")
                                .content(userMessage)
                                .build()
                ))
                .build();
    }

    /**
     * Создание запроса для мультимодальной модели с изображением
     * @param model название модели (например, "llava")
     * @param userMessage текстовый запрос
     * @param base64Image изображение в формате base64
     * @return готовый DTO запроса
     */
    public static OllamaChatRequest createMultimodalRequest(
            String model,
            String userMessage,
            String base64Image) {
        return OllamaChatRequest.builder()
                .model(model)
                .stream(false)
                .messages(List.of(
                        Message.builder()
                                .role("user")
                                .content(userMessage)
                                .images(List.of(base64Image))
                                .build()
                ))
                .build();
    }
}