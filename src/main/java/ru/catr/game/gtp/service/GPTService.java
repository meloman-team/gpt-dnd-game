package ru.catr.game.gtp.service;

public interface GPTService {
    String generateText(String prompt, String context);
}
