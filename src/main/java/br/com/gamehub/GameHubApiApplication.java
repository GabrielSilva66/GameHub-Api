package br.com.gamehub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for the GameHub API.
 * <p>
 * This class is responsible for bootstrapping the Spring Boot application.
 * It also implements the CommandLineRunner interface to execute any code after the application has started.
 * The application enables scheduling and transaction management features.
 * </p>
 *
 * @author Gabriel Victor and Pedro Lucas
 * @since 2025-01-23
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class GameHubApiApplication implements CommandLineRunner {

	/**
	 * Main method that starts the Spring Boot application.
	 *
	 * @param args the command-line arguments (if any).
	 */
	public static void main(String[] args) {
		SpringApplication.run(GameHubApiApplication.class, args);
	}

	/**
	 * CommandLineRunner method executed after the application has started.
	 * This method is a placeholder to add custom logic to be executed after the application context is initialized.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	@Override
	public void run(String... args) throws Exception {
		// Placeholder for any startup logic
		System.out.println("GameHub API is running...");
	}
}
