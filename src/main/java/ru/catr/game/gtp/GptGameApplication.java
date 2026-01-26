package ru.catr.game.gtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GptGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptGameApplication.class, args);
	}

}
