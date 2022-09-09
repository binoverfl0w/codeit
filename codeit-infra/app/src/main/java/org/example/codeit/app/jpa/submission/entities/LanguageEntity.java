package org.example.codeit.app.jpa.submission.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.submission.Language;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "language")
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public static LanguageEntity fromDomainLanguage(Language language) {
        return new LanguageEntity(
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
