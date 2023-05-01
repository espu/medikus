package spring23.sp.medikus.domain;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long activityId;
	private String time;
	private String activityInput;
	private int duration;
	private int stress;
	private String mood;
	private double water;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "healthid")
	private Health health;
	
	public Activity() {
		
	}
	
	public Activity(String time, String activityInput, int duration, int stress, String mood, double water, Health health) {
		super();
		this.time = time;
		this.activityInput = activityInput;
		this.duration = duration;
		this.stress = stress;
		this.mood = mood;
		this.water = water;
		this.health = health;
	}

	// Getters
	public Long getActivityId() {
		return activityId;
	}

	public String getTime() {
		return time;
	}

	public String getActivityInput() {
		return activityInput;
	}

	public int getDuration() {
		return duration;
	}

	public int getStress() {
		return stress;
	}

	public String getMood() {
		return mood;
	}

	public double getWater() {
		return water;
	}

	public Health getHealth() {
		return health;
	}

	// Setters
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setActivityInput(String activityInput) {
		this.activityInput = activityInput;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public void setHealth(Health health) {
		this.health = health;
	}

	@Override
	public String toString() {
		return "Time: " + time + ", activityInput: " + activityInput + ", duration: " + duration + ", stress: " + stress
				+ ", mood: " + mood + ", water: " + water + "; health: " + health.getDate() + " " + health.getMotto();
	}
}
