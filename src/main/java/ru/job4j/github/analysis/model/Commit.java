package ru.job4j.github.analysis.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Schema(description = "Commit Model Information")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "commits")
public class Commit {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String author;
    private Instant date;
    @Column(nullable = false, unique = true)
    private String sha;
    @ManyToOne
    @JoinColumn(name = "repository_id")
    private GitHubRepository gitHubRepository;
}
