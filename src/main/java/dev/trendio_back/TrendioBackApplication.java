package dev.trendio_back;

import dev.trendio_back.service.InitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrendioBackApplication {

	private static InitializerService initializerService;

	@Autowired
	public void setInitialLoader(InitializerService initializerService) {
		TrendioBackApplication.initializerService = initializerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TrendioBackApplication.class, args);
		initializerService.initial();
	}

}
