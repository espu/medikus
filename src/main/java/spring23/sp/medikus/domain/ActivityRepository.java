package spring23.sp.medikus.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    // Find activity by input
    List<Activity> findByActivityInput(String activityInput);

    // Find activities by the experienced mood
    List<Activity> findByMood(String mood);
}
