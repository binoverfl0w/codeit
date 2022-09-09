package org.example.codeit.app.rest.profile.dto;

//import com.example.app.common.utility.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.codeit.app.rest.View;

@Getter
@Setter
@Builder

@JsonView(View.Default.class)
public class AuthTokenDTO {
    @JsonProperty(value = "access_token", index = 1)
    private String access_token;

    @JsonProperty(value = "token_type", index = 2)
    private String token_type;

    @JsonProperty(value = "expires_in", index = 3)
    private int expires_in;
}
