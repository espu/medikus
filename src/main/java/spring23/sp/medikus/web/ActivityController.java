package spring23.sp.medikus.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import spring23.sp.medikus.domain.Activity;
import spring23.sp.medikus.domain.ActivityRepository;
import spring23.sp.medikus.domain.User;
import spring23.sp.medikus.domain.UserRepository;
import spring23.sp.medikus.domain.Health;
import spring23.sp.medikus.domain.HealthRepository;

@CrossOrigin
@Controller
public class ActivityController {

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	HealthRepository healthRepository;

	@Autowired
	UserRepository userRepository;

	//Redirect to list of activities in relation to the health journal
	@GetMapping("/healthlist/{id}/activities/")
	public String activityList(@PathVariable("id") Long healthId, Model model) {
		model.addAttribute("health", healthRepository.findById(healthId).get());
		model.addAttribute("activity", new Activity());
		return "activitylist";
	}

	// Save a new activity to the list of activities and redirect upon completion
	@PostMapping("/healthlist/{id}/activities/save")
	public String addActivity(@PathVariable("id") Long healthId, @ModelAttribute Activity activity) {
		activity.setTime(activity.getTime());
		activity.setActivityInput(activity.getActivityInput());
		activity.setDuration(activity.getDuration());
		activity.setStress(activity.getStress());
		activity.setMood(activity.getMood());
		activity.setWater(activity.getWater());
		activity.setHealth(healthRepository.findById(healthId).get());
		activityRepository.save(activity);
		return "redirect:/healthlist/{id}/activities/";
	}

	// Delete a single activity and redirect to list of activities page upon completion
	@GetMapping("/healthlist/{id}/activities/delete/{activityid}")
	public String removeActivity(@PathVariable("id") Long healthId, @PathVariable("activityid") Long activityId) {
		activityRepository.delete(activityRepository.findById(activityId).get());
		return "redirect:/healthlist/{id}/activities/";
	}

	// REST

	// Get all activities
	@GetMapping("/api/activity")
	public @ResponseBody List<Activity> activityListRest() {
		return (List<Activity>) activityRepository.findAll();
	}

	// Get activity by id
	@GetMapping("/api/activity/{id}")
	public @ResponseBody Optional<Activity> findActivityRest(@PathVariable("id") Long activityId) {
		return activityRepository.findById(activityId);
	}

	// Get all activities of a health card by health id
	@GetMapping("/api/activity/health/{id}")
	public @ResponseBody List<Activity> findActivitysByHealthId(@PathVariable("id") Long healthId) {
		Health health = healthRepository.findById(healthId).get();
		return health.getActivities();
	}

	// Save new activity
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/api/activity")
	public @ResponseBody Activity saveActivityRest(@RequestBody Activity activity) {
		return activityRepository.save(activity);
	}
}