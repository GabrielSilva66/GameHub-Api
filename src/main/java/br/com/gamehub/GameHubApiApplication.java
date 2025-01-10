package br.com.gamehub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameHubApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GameHubApiApplication.class, args);
	}

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("GameHub API is running...");
	}
}
