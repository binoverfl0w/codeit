package org.example.codeit.app.rest.profile.dto;

//import com.example.app.common.utility.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.profile.Profile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Default.class)
public class ProfileDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "fullname", index = 1)
    private String fullname;

    @JsonProperty(value = "username", index = 2)
    private String username;

    @JsonProperty(value = "email", index = 3)
    private String email;

    @JsonView(View.Privileged.class)
    @JsonProperty(value = "password", index = 4)
    private String password;

    @JsonProperty(value = "score", index = 5)
    private Integer score;

    @JsonView(View.Privileged.class)
    @JsonProperty(value = "locked", index = 6)
    private Boolean locked;

    @JsonProperty(value = "enabled", index = 7)
    private Boolean enabled;

//    @JsonView(View.Admin.class)
    @JsonProperty(value = "role", index = 8)
    private RoleDTO role;

//    @JsonView(View.Admin.class)
//    @JsonProperty(value = "authorities", index = 9)
//    private Set<String> authorities;

    public void setId(Long id) {
        this.id = id;
    }

    public static ProfileDTO fromDomainProfile(Profile profile) {
        return new ProfileDTO(
                profile.getId(),
                profile.getFullname(),
                profile.getUsername(),
                profile.getEmail(),
                profile.getPassword(),
                profile.getScore(),
                profile.isLocked(),
                profile.isEnabled(),
                RoleDTO.fromDomainRole(profile.getRole())
        );
    }

    public Profile toDomainProfile() {
        return new Profile(
                this.getId(),
                this.getFullname(),
                this.getUsername(),
                this.getEmail(),
                this.getPassword(),
                this.getScore(),
                this.getLocked(),
                this.getEnabled(),
                this.getRole() == null ? null : this.getRole().toDomainRole()
        );
    }
}
