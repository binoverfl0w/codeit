package org.example.codeit.app.judge0;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.app.rest.View;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonView(View.Default.class)
public class StatusDTO {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "description")
    private String description;
}
