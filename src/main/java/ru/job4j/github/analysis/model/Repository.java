package ru.job4j.github.analysis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
@Entity
@Table(name = "repositories")
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    @JsonProperty("full_name")
    private String fullName;
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Instant updatedAt;
    private String language;
    @Column(name = "html_url")
    @JsonProperty("html_url")
    private String htmlUrl;

}
