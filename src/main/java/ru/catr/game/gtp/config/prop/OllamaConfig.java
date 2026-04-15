package ru.catr.game.gtp.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ollama")
public record OllamaConfig(
        String model
) {}
