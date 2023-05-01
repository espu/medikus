package spring23.sp.medikus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import spring23.sp.medikus.web.UserDetailServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
        	.authorizeRequests().requestMatchers("/css/**").permitAll()	// Enable CSS without authentication;
        	.and()
        	.authorizeRequests().requestMatchers("/signup", "/saveuser").permitAll()
            .and()
			.authorizeRequests().anyRequest().authenticated()
	        .and()
	        .formLogin()
	        	.loginPage("/login")				// Override default login page with custom implementation;
	        	.defaultSuccessUrl("/home", true)	// Present home page after successful authentication;
	        	.permitAll()
	        .and()
	        .logout()
	        	.permitAll().invalidateHttpSession(true)
	        	.and()
			.httpBasic();
		return http.build();
	}

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

}