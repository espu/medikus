package spring23.sp.medikus.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import spring23.sp.medikus.domain.SignupForm;

import spring23.sp.medikus.domain.User;
import spring23.sp.medikus.domain.UserRepository;
import spring23.sp.medikus.domain.Activity;
import spring23.sp.medikus.domain.Health;
import spring23.sp.medikus.domain.HealthRepository;

@CrossOrigin
@Controller
public class HealthController {

	@Autowired
	HealthRepository healthRepository;

	@Autowired
	UserRepository userRepository;

	//Redirect to login page
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	//Redirect to signup page
	@GetMapping("/signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}

	// Redirect to home page
	@GetMapping("/home")
	public String home(Model model, Authentication auth, HttpSession session) {
		User user = userRepository.findByUsername(auth.getName());
		model.addAttribute("healths", healthRepository.findAllByUserOrderByDate(user));
		
		// Check if current users HTTP Session has logged in for the first time
		boolean hasSeenPopup = false;
        Object popupSeenAttribute = session.getAttribute("popupSeen");
        if (popupSeenAttribute != null && (boolean)popupSeenAttribute == true) {
            hasSeenPopup = true;
        }
        else {
            session.setAttribute("popupSeen", true);
        }
        model.addAttribute("hasSeenPopup", hasSeenPopup);
        return "home";
    }
	

	// Redirect to about page
	@GetMapping("/about")
	public String about(Model model, Authentication auth) {
		User user = userRepository.findByUsername(auth.getName());
		model.addAttribute("healths", healthRepository.findAllByUserOrderByDate(user));
		return "about";
	}

	// Redirect to healthlist page, which lists activities for a selected health journal and is sorted by date
	@GetMapping("/healthlist")
	public String healthList(Model model, Authentication auth) {
		User user = userRepository.findByUsername(auth.getName());
		model.addAttribute("healths", healthRepository.findAllByUserOrderByDate(user));
		model.addAttribute("activity", new Activity());
		return "healthlist";
	}

	// Redirect to add health journal page
	@GetMapping("/addhealthjournal")
	public String addHealth(Model model) {
		model.addAttribute("health", new Health());
		return "addhealthjournal";
	}

	// Save a new health journal and redirect to home page
	@PostMapping("/save")
	public String saveHealth(Health health, RedirectAttributes redirectAttributes, Authentication auth) {
		User user = userRepository.findByUsername(auth.getName());
		health.setUser(user);
		healthRepository.save(health);
		redirectAttributes.addAttribute("id", health.getHealthId());
		return "redirect:home";
	}

	// Edit health journal entry and redirect to home page upon completion
	@GetMapping("/edit/{id}")
	public String editHealth(@PathVariable("id") Long healthId, Model model) {
		model.addAttribute("health", healthRepository.findById(healthId));
		return "edithealthjournal";
	}

	// Delete a single health journal and all activities associated - redirect to home page upon completion
	@GetMapping("/delete/{id}")
	public String deleteHealth(@PathVariable("id") Long healthId) {
		healthRepository.deleteById(healthId);
		return "redirect:../home";
	}
	
	//Checking if the user has already seen the the introduction popup article
	@GetMapping("/showArticlePopup")
	public String showArticlePopup(HttpSession session) {
	    // Set flag indicating user has not yet seen popup
	    session.setAttribute("articlePopupShown", false);

	    // Return view for article popup
	    return "articlePopup";
	}

	// REST 

	// Get all health journals
	@GetMapping("/api/health")
	public @ResponseBody List<Health> healthListRest() {
		return (List<Health>) healthRepository.findAll();
	}

	// Get health by healthId
	@GetMapping("/api/health/{id}")
	public @ResponseBody Optional<Health> findHealthRest(@PathVariable("id") Long healthId) {
		return healthRepository.findById(healthId);
	}

	// Save a new health journal as ADMIN ONLY
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/api/health")
	public @ResponseBody Health saveHealthRest(@RequestBody Health health) {
		return healthRepository.save(health);
	}
}