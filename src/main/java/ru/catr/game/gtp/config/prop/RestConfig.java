package ru.catr.game.gtp.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.http")
public record RestConfig(
        int connectTimeout,
        int readTimeout
) {}
