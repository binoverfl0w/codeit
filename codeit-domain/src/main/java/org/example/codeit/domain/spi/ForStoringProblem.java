package org.example.codeit.domain.spi;

import hexarch.AbstractPage;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;

import java.util.Optional;

public interface ForStoringProblem {
    Problem storeProblem(Problem problem);

    Optional<Problem> getProblemById(Long id);

    AbstractPage<Problem> getProblemsPage(int page, int size);

    void deleteProblemById(Long id);

    Category storeCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Optional<Category> getCategoryByName(String name);

    AbstractPage<Category> getCategoriesPage(int page, int size);

    void deleteCategoryById(Long id);

    Category getDefaultCategory();
}
