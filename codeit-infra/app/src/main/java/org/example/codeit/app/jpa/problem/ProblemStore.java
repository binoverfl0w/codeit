package org.example.codeit.app.jpa.problem;

import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import hexarch.Constants.*;
import org.example.codeit.app.jpa.problem.entities.CategoryEntity;
import org.example.codeit.app.jpa.problem.entities.ProblemEntity;
import org.example.codeit.app.jpa.problem.repositories.CategoryRepository;
import org.example.codeit.app.jpa.problem.repositories.ProblemRepository;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.spi.ForStoringProblem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.stream.Collectors;

public class ProblemStore implements ForStoringProblem {

    private ProblemRepository problemRepository;
    private CategoryRepository categoryRepository;

    public ProblemStore(ProblemRepository problemRepository, CategoryRepository categoryRepository) {
        this.problemRepository = problemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Problem storeProblem(Problem problem) {
        return problemRepository.save(ProblemEntity.fromDomainProblem(problem)).toDomainProblem();
    }

    @Override
    public Optional<Problem> getProblemById(Long id) {
        return problemRepository.findById(id).map(ProblemEntity::toDomainProblem);
    }

    @Override
    public AbstractPage<Problem> getProblemsPage(int page, int size) {
        Page<ProblemEntity> problemsPage = problemRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                problemsPage.getTotalElements(),
                problemsPage.getTotalPages(),
                problemsPage.getNumber(),
                problemsPage.getContent().stream().map(ProblemEntity::toDomainProblem).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    @Override
    public Category storeCategory(Category category) {
        return categoryRepository.save(CategoryEntity.fromDomainCategory(category)).toDomainCategory();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(CategoryEntity::toDomainCategory);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return Optional.of(categoryRepository.findByName(name).toDomainCategory());
    }

    @Override
    public AbstractPage<Category> getCategoriesPage(int page, int size) {
        Page<CategoryEntity> categoriesPage = categoryRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                categoriesPage.getTotalElements(),
                categoriesPage.getTotalPages(),
                categoriesPage.getNumber(),
                categoriesPage.getContent().stream().map(CategoryEntity::toDomainCategory).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getDefaultCategory() {
        return categoryRepository.findByName(CATEGORY.GENERAL).toDomainCategory();
    }
}
