package org.example.codeit.domain.problem;

import java.time.LocalDateTime;

public class Problem {
    private Long id;
    private String title;
    private String body;
    private Category category;
    private int difficulty = 1;
    private String input;
    private String expectedOutput;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Problem(Long id, String title, String body, Category category, int difficulty, String input, String expectedOutput, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.difficulty = difficulty;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.createdAt = createdAt;
    }

    public Problem(String title, String body, Category category, int difficulty, String input, String expectedOutput, LocalDateTime createdAt) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.difficulty = difficulty;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.createdAt = createdAt;
    }

    public Problem(String title, String body, Category category, int difficulty, String input, String expectedOutput) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.difficulty = difficulty;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
