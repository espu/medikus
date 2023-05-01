package spring23.sp.medikus.domain;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "userlist") //Renaming table to avoid overlap with "User"
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Health> health;

    public User() {
    }

    public User(String username, String passwordHash, String role) {
        super();
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    //Getters and setters
    
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }
    
    public List<Health> getHealth() {
        return health;
    }
    
        public void setId(Long id) {
        this.id = id;
    }
        
    public void setUsername(String username) {
        this.username = username;
    }

	public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setHealth(List<Health> health) {
        this.health = health;
    }
}
