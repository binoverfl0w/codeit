package org.example.codeit.domain.profile;

public class Profile {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private Integer score = 0;
    private Boolean enabled = true;
    private Boolean locked = false;
    private Role role;

    public Profile(Long id, String fullname, String username, String email, String password, Integer score, Boolean enabled, Boolean locked, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = score;
        this.enabled = enabled;
        this.locked = locked;
        this.role = role;
    }

    public Profile(Long id, String fullname, String username, String email, String password, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Profile(String fullname, String username, String email, String password, Role role) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void increaseScore(int score) { this.score += score; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
