package ru.job4j.github.analysis.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
@Entity
@Table(name = "commits")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String author;
    private Instant date;
    @Column(nullable = false, unique = true)
    private String sha;
    @ManyToOne
    @JoinColumn(name = "repository_id")
    private Repository repository;
}
