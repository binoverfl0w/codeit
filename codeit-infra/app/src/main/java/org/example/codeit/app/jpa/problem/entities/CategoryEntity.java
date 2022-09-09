package org.example.codeit.app.jpa.problem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.problem.Category;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public static CategoryEntity fromDomainCategory(Category category) {
        return new CategoryEntity(category.getId(), category.getName());
    }

    public Category toDomainCategory() {
        return new Category(this.getId(), this.getName());
    }
}
