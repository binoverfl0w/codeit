package org.example.codeit.app.rest.profile.dto;

//import com.example.app.common.utility.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.codeit.app.rest.PageDTO;
import org.example.codeit.app.rest.View;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Default.class)
public class ProfilesDTO extends PageDTO {
    @JsonProperty(value = "profiles", index = 1)
    private List<ProfileDTO> profiles = new ArrayList<>();
}
