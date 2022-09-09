package org.example.codeit.app.rest.submission.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.submission.Language;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@JsonView(View.Default.class)
public class LanguageDTO {
    @JsonProperty(value = "id", index = 1)
    private Long id;
    @JsonProperty(value = "name", index = 2)
    private String name;

    public static LanguageDTO fromDomainLanguage(Language language) {
        return new LanguageDTO(
                language.getId(),
                language.getName()
        );
    }

    public Language toDomainLanguage() {
        return new Language(
                this.getId(),
                this.getName()
        );
    }
}
