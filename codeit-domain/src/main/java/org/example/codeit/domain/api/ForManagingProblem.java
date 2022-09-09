package org.example.codeit.domain.api;

import hexarch.AbstractPage;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;

public interface ForManagingProblem {
    AbstractPage<Problem> getProblemsPage(int page, int size);

    Problem getProblem(Long id);

    Problem createProblem(Problem problem);

    Problem updateProblem(Problem problem);

    void deleteProblem(Long id);

    AbstractPage<Category> getCategoriesPage(int page, int size);

    Category getCategory(Long id);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}
