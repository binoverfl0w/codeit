package org.example.codeit.domain.problem;

import hexarch.AccessDeniedException;
import hexarch.Constants.*;
import hexarch.AbstractPage;
import org.example.codeit.domain.api.ForManagingProblem;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.spi.ForStoringProblem;

public class ProblemManager implements ForManagingProblem {

    private ForStoringProblem problemStore;
    private AuthenticationContext authenticationContext;

    public ProblemManager(ForStoringProblem problemStore, AuthenticationContext authenticationContext) {
        this.problemStore = problemStore;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public AbstractPage<Problem> getProblemsPage(int page, int size) {
        return problemStore.getProblemsPage(page, size);
    }

    @Override
    public Problem getProblem(Long id) {
        return problemStore.getProblemById(id).orElseThrow(() -> new ProblemNotFoundException("id", id.toString()));
    }

    @Override
    public Problem createProblem(Problem problem) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROBLEMS)) throw new AccessDeniedException();
        if (problem.getCategory() == null) problem.setCategory(problemStore.getDefaultCategory());
        problem.setCategory(problemStore.getCategoryByName(problem.getCategory().getName()).orElseThrow(() -> new CategoryNotFoundException("name", problem.getCategory().getName())));
        return problemStore.storeProblem(problem);
    }

    @Override
    public Problem updateProblem(Problem problem) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROBLEMS)) throw new AccessDeniedException();
        Problem toUpdate = problemStore.getProblemById(problem.getId()).orElseThrow(() -> new ProblemNotFoundException("id", problem.getId().toString()));
        toUpdate.setCategory(problemStore.getCategoryByName(toUpdate.getCategory().getName()).orElseThrow(() -> new CategoryNotFoundException("name", problem.getCategory().getName())));
        return problemStore.storeProblem(toUpdate);
    }

    @Override
    public void deleteProblem(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        problemStore.deleteProblemById(id);
    }

    @Override
    public AbstractPage<Category> getCategoriesPage(int page, int size) {
        return problemStore.getCategoriesPage(page, size);
    }

    @Override
    public Category getCategory(Long id) {
        return problemStore.getCategoryById(id).orElseThrow(() -> new CategoryNotFoundException("id", id.toString()));
    }

    @Override
    public Category createCategory(Category category) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_CATEGORIES)) throw new AccessDeniedException();
        return problemStore.storeCategory(category);
    }

    @Override
    public Category updateCategory(Category category) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_CATEGORIES)) throw new AccessDeniedException();
        Category toUpdate = problemStore.getCategoryById(category.getId()).orElseThrow(() -> new CategoryNotFoundException("id", category.getId().toString()));
        // TODO check constraints
        if (category.getName() != null) toUpdate.setName(category.getName());
        return problemStore.storeCategory(toUpdate);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_CATEGORIES)) throw new AccessDeniedException();
        problemStore.deleteCategoryById(id);
    }
}
