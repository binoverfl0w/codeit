package org.example.codeit.app.judge0;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonView(View.Default.class)
public class SubmissionJudgeDTO {
    @JsonProperty(value = "source_code", index =  1)
    private String sourceCode;
    @JsonProperty(value = "language_id", index = 2)
    private Integer languageId;
    @JsonProperty(value = "stdin", index = 3)
    private String stdin;
    @JsonProperty(value = "expected_output", index = 4)
    private String expectedOutput;
    @JsonProperty(value = "status", index = 5)
    private StatusDTO status;
    @JsonProperty(value = "created_at", index = 6)
    private LocalDateTime createdAt;
    @JsonProperty(value = "finished_at", index = 7)
    private LocalDateTime finishedAt;
    @JsonProperty(value = "token", index = 8)
    private String token;
    @JsonProperty(value = "time", index = 9)
    private Float time;
    @JsonProperty(value = "memory", index = 10)
    private Float memory;
}
