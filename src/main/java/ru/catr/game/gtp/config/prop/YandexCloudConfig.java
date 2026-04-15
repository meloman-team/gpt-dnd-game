package ru.catr.game.gtp.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "yandex.cloud")
public record YandexCloudConfig(
        String apiKey,
        String folderId,
        String model,
        String modelVersion,
        String apiUrl
) {}
