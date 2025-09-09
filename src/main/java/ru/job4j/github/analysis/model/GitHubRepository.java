package ru.job4j.github.analysis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Schema(description = "GitHubRepository Model Information")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "repositories")
public class GitHubRepository {
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
