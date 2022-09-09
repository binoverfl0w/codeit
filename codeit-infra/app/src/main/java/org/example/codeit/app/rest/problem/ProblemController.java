package org.example.codeit.app.rest.problem;

import hexarch.AbstractPage;
import org.example.codeit.app.rest.Response;
import org.example.codeit.app.rest.problem.dto.CategoriesDTO;
import org.example.codeit.app.rest.problem.dto.CategoryDTO;
import org.example.codeit.app.rest.problem.dto.ProblemDTO;
import org.example.codeit.app.rest.problem.dto.ProblemsDTO;
import org.example.codeit.domain.api.ForManagingProblem;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private ForManagingProblem problemManager;

    public ProblemController(ForManagingProblem problemManager) {
        this.problemManager = problemManager;
    }

    @GetMapping
    public ResponseEntity<Object> getProblems(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        AbstractPage<Problem> problems = problemManager.getProblemsPage(page, size);
        return Response.handleGet(
                ProblemsDTO.builder()
                        .problems(problems.getItems().stream().map(ProblemDTO::fromDomainProblem).collect(Collectors.toList()))
                        .currentPage(problems.getCurrentPage())
                        .totalPages(problems.getTotalPages())
                        .totalItems(problems.getTotalItems())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProblem(@PathVariable Long id) {
        return Response.handleGet(
                ProblemDTO.fromDomainProblem(problemManager.getProblem(id))
        );
    }

    @PostMapping
    public ResponseEntity<Object> createProblem(@RequestBody ProblemDTO problemDTO) {
        return Response.handlePost(
                ProblemDTO.fromDomainProblem(problemManager.createProblem(problemDTO.toDomainProblem()))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProblem(@PathVariable Long id, @RequestBody ProblemDTO problemDTO) {
        // TODO check id in path variable with id in body
        return Response.handlePatch(
                ProblemDTO.fromDomainProblem(problemManager.createProblem(problemDTO.toDomainProblem()))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProblem() {
        return Response.handleDelete();
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<Object> getCategories(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        AbstractPage<Category> categories = problemManager.getCategoriesPage(page, size);
        return Response.handleGet(
                CategoriesDTO.builder()
                        .categories(categories.getItems().stream().map(CategoryDTO::fromDomainCategory).collect(Collectors.toList()))
                        .currentPage(categories.getCurrentPage())
                        .totalPages(categories.getTotalPages())
                        .totalItems(categories.getTotalItems())
                        .build()
        );
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable Long id) {
        return Response.handleGet(
                CategoryDTO.fromDomainCategory(problemManager.getCategory(id))
        );
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return Response.handleGet(
                CategoryDTO.fromDomainCategory(problemManager.createCategory(categoryDTO.toDomainCategory()))
        );
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        // TODO check id in path variable with id in request body
        return Response.handlePatch(
                CategoryDTO.fromDomainCategory(problemManager.updateCategory(categoryDTO.toDomainCategory()))
        );
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        problemManager.deleteCategory(id);
        return Response.handleDelete();
    }
}
