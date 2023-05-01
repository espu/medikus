package spring23.sp.medikus.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignupForm {
    
    @NotEmpty
    @Size(min = 3, max = 20)
    private String username = "";

    @NotEmpty
    @Size(min = 5, max = 20)
    private String password = "";

    @NotEmpty
    @Size(min = 5, max = 20)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "USER"; //Assign new users the USER ONLY role

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getPasswordCheck() {
        return passwordCheck;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
