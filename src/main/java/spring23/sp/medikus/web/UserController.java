package spring23.sp.medikus.web;

import jakarta.validation.Valid;

import spring23.sp.medikus.domain.SignupForm;
import spring23.sp.medikus.domain.User;
import spring23.sp.medikus.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// See a list of all users - ADMIN ONLY
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/userlist")
	public String userList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "userlist";
	}

	// Delete a user - ADMIN ONLY
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/userlist/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, RedirectAttributes redirectAttributes) {
		userRepository.deleteById(userId);
		redirectAttributes.addFlashAttribute("successMessage", "You have deleted a user!");
		return "redirect:../../userlist";
	}

	// Save a new user through the signup form upon checking requirements
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	// Checking for form errors
        if (bindingResult.hasErrors()) {
            // Rejecting signup form because of errors
            return "signup";
        }

        // Checking password match
        if (!signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "err.passCheck", "Password does not match");
            return "signup";
        }

        // Create a new user object
        String password = signupForm.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPassword = bCryptPasswordEncoder.encode(password);
        String username = signupForm.getUsername();
        User newUser = new User(username, hashPassword, "USER");

        // Checking if the username already exists
        if (userRepository.findByUsername(username) != null) {
            bindingResult.rejectValue("username", "err.username", "Username already exists");
            return "signup";
        }

        //Saving new user
        userRepository.save(newUser);

        // Sign up completed and redirect to login page
        return "redirect:/login";
    }
}