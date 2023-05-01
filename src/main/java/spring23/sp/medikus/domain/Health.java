package spring23.sp.medikus.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Health {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long healthId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") //Date format
	private LocalDate date;
	
	private String motto;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "health")
	private List<Activity> activities;

	@ManyToOne
	@JoinColumn(name = "id")
	private User user;

	public Health() {

	}

	public Health(LocalDate date, String motto) {
		super();
		this.date = date;
		this.motto = motto;
	}

	public Health(LocalDate date, String motto, List<Activity> activities) {
		super();
		this.date = date;
		this.motto = motto;
		this.activities = activities;
	}

	public Health(LocalDate date, String motto, List<Activity> activities, User user) {
		super();
		this.date = date;
		this.motto = motto;
		this.activities = activities;
		this.user = user;
	}
	
    //Getters and setters

	public Long getHealthId() {
		return healthId;
	}

	public LocalDate getDate() {
		return date;
	}

	// Formatting LocalDate
	public String getDateFormatted() {
		DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return standardFormat.format(date);
	}

	public String getMotto() {
		return motto;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public User getUser() {
		return user;
	}

	public void setHealthId(Long healthId) {
		this.healthId = healthId;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public void setActivity(List<Activity> activities) {
		this.activities = activities;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addActivity(Activity activities) {
		getActivities().add(activities);
		activities.setHealth(this);
	}

	public void removeActivity(Activity activities) {
		getActivities().remove(activities);
		activities.setHealth(null);
	}

	@Override
	public String toString() {
		return "Health: " + getDateFormatted() + ", Motto: " + motto;
	}
}
