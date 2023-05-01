package spring23.sp.medikus;

import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import spring23.sp.medikus.domain.Activity;
import spring23.sp.medikus.domain.ActivityRepository;
import spring23.sp.medikus.domain.Health;
import spring23.sp.medikus.domain.HealthRepository;
import spring23.sp.medikus.domain.User;
import spring23.sp.medikus.domain.UserRepository;

@SpringBootApplication
public class MedikusApplication {
	private static final Logger log = LoggerFactory.getLogger(MedikusApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MedikusApplication.class, args);
	}

	// Initial database population
	@Bean
	public CommandLineRunner demo(HealthRepository healthRepository, ActivityRepository activityRepository, UserRepository userRepository) {
		return (args) -> {
			log.info("Populating table");
			userRepository.deleteAll();

			User user = new User("user", "$2a$10$uHgwYhqLKumssPxb1ooWDOfgCn3SgFkU5CVsK3KXp9BdSjs2Tf8cq", "USER");
			User admin = new User("admin", "$2a$10$CuDAFP7yMSmO8qNUsUmLgOFcytLlPXgsN5Uk23Zo9K2i6asRgMu..", "ADMIN");

			userRepository.save(user);
			userRepository.save(admin);

			Health health1 = new Health(LocalDate.of(2023, 5, 2), "The secret of getting ahead is getting started", new ArrayList<>(), admin);
			Health health2 = new Health(LocalDate.of(2023, 5, 4), "Everything you can imagine is real", new ArrayList<>(), admin);
			Health health3 = new Health(LocalDate.of(2023, 7, 9), "A fresh start", new ArrayList<>(), user);
			
			healthRepository.save(health1);
			healthRepository.save(health2);
			healthRepository.save(health3);
			
			activityRepository.save(new Activity("Morning", "Yoga", 30, 3, "Happy", 1, health1));
			activityRepository.save(new Activity("Afternoon", "Meditation", 20, 1, "Relaxed", 0.6, health1));
			activityRepository.save(new Activity("Night", "Cry", 60, 10, "Sad", -1, health1));
			activityRepository.save(new Activity("Lunch", "Ate filling food", 25, 2, "Happy", 0.5, health2));


			log.info("Fetch all health journals");
			for (Health health : healthRepository.findAll()) {
				log.info(health.toString());
			}
		};
	}
}
