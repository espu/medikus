package spring23.sp.medikus;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring23.sp.medikus.domain.Activity;
import spring23.sp.medikus.domain.User;
import spring23.sp.medikus.domain.Health;

import spring23.sp.medikus.domain.ActivityRepository;
import spring23.sp.medikus.domain.UserRepository;
import spring23.sp.medikus.domain.HealthRepository;

import spring23.sp.medikus.web.ActivityController;
import spring23.sp.medikus.web.UserController;
import spring23.sp.medikus.web.HealthController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MedikusApplicationTests {

	@Autowired
	UserController userController;
	
	@Autowired
	HealthController healthController;
	
	@Autowired
	ActivityController activityController;

	@Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthRepository healthRepository;

	// Controller instanced properly
	@Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
		assertThat(healthController).isNotNull();
		assertThat(activityController).isNotNull();
	}
	
	//Test userRepository by finding user by username
    @Test
    public void findByUsernameShouldReturnUser() {
        User user = userRepository.findByUsername("admin");
        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

    //Test userRepository by creating new user
    @Test
    public void createUser() {
        User user = new User("newuser", "$2a$10$uHgwYhqLKumssPxb1ooWDOfgCn3SgFkU5CVsK3KXp9BdSjs2Tf8cq", "USER");
        userRepository.save(user);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("newuser");
        assertThat(user.getHealth()).isNull();
    }

    //Test healthRepository by creating a new health journal
    @Test
    public void createHealth() {
    	Health health = new Health(LocalDate.of(2023, 5, 2), "A fresh start", new ArrayList<>());
        healthRepository.save(health);
        assertThat(health).isNotNull();
        assertThat(health.getMotto()).isEqualTo("A fresh start");
        assertThat(health.getDateFormatted()).isEqualTo("02.05.2023");
        assertThat(health.getActivities().size()).isEqualTo(0);
    }

    //Test to find health journal by id
    @Test
    public void findByIdShouldReturnHealth() {
        long id = 1;
        Health health = healthRepository.findById(Long.valueOf(id)).get();
        assertThat(health).isNotNull();
        assertThat(health.getMotto()).isEqualTo("The secret of getting ahead is getting started");
        assertThat(health.getUser()).isNotNull();
        assertThat(health.getUser().getUsername()).isEqualTo("admin");
    }

    //Test activityRepository by creating a new activity
    @Test
    public void createBlankActivity() {
        Activity activity = new Activity();
        activityRepository.save(activity);
        assertThat(activity).isNotNull();
        assertThat(activity.getTime()).isNull();
        assertThat(activity.getActivityInput()).isNull();
    }

    //Test activityRepository by deleting an activity by id
    @Test
    public void deleteActivity() {
        long id = 2;
        activityRepository.deleteById(Long.valueOf(id));
        assertThat(activityRepository.findById(id)).isEmpty();
        assertThat(activityRepository.count()).isEqualTo(4);
    }
}
