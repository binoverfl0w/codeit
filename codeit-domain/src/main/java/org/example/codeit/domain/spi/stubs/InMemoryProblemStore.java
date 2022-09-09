package org.example.codeit.domain.spi.stubs;

import hexarch.Constants.*;
import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.spi.ForStoringProblem;

import java.util.HashMap;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class InMemoryProblemStore implements ForStoringProblem {

    HashMap<Long, Problem> problems = new HashMap<>();
    HashMap<Long, Category> categories = new HashMap<>();

    @Override
    public Problem storeProblem(Problem problem) {
        if (problem.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            problem.setId(id);
        }
        problems.put(problem.getId(), problem);
        return problems.get(problem.getId());
    }

    @Override
    public Optional<Problem> getProblemById(Long id) {
        return Optional.of(problems.get(id));
    }

    @Override
    public AbstractPage<Problem> getProblemsPage(int page, int size) {
        return new AbstractPageImpl<Problem>(
                problems.size(),
                problems.size() / size,
                page,
                problems.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteProblemById(Long id) {
        problems.remove(id);
    }

    @Override
    public Category storeCategory(Category category) {
        if (category.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            category.setId(id);
        }
        categories.put(category.getId(), category);
        return categories.get(category.getId());
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.of(categories.get(id));
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categories.values().stream().filter(c -> c.getName().equals(name)).findFirst();
    }

    @Override
    public AbstractPage<Category> getCategoriesPage(int page, int size) {
        return new AbstractPageImpl<Category>(
                categories.size(),
                categories.size() / size,
                page,
                categories.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteCategoryById(Long id) {
        categories.remove(id);
    }

    @Override
    public Category getDefaultCategory() {
        // TODO default category
        return categories.values().stream().filter(c -> c.getName().equals(CATEGORY.GENERAL)).findAny().orElse(null);
    }
}
