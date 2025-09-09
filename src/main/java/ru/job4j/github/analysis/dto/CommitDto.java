package ru.job4j.github.analysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitDto {
    private String sha;
    private Commit commit;

    public CommitDto(String sha, String authorName, String message, Instant date) {
        this.sha = sha;

        Author author = new Author();
        author.setName(authorName);
        author.setDate(date);

        Commit commit = new Commit();
        commit.setAuthor(author);
        commit.setMessage(message);

        this.commit = commit;
    }

    @Data
    public static class Commit {
        private Author author;
        private String message;
    }

    @Data
    public static class Author {
        private String name;
        private Instant date;
    }
}
