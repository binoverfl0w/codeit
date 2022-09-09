package org.example.codeit.domain.submission;

public class Language {
    private Long id;
    private String name;

    public Language(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Language(String name) {
        this.name = name;
    }

    public Language(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
