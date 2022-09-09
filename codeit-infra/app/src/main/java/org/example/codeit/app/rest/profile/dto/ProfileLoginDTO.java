package org.example.codeit.app.rest.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

//import javax.validation.constraints.NotBlank;

@Getter

public class ProfileLoginDTO {
    @JsonProperty(value = "username")
//    @NotBlank(message = "Username can't be empty.")
    private String username;

    @JsonProperty(value = "password")
//    @NotBlank(message = "Password can't be empty.")
    private String password;
}
