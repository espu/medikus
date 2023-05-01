package spring23.sp.medikus.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HealthRepository extends CrudRepository<Health, Long> {
    // Find health journals based on the date
    List<Health> findByDate(LocalDate date);

    // Sorting health journals by date
    List<Health> findAllByOrderByDate();

    // Find user's health journals sorted by date
    List<Health> findAllByUserOrderByDate(User user);
}

