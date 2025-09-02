package ru.job4j.github.analysis.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryStore extends JpaRepository<ru.job4j.github.analysis.model.Repository, Long> {
    ru.job4j.github.analysis.model.Repository findByFullName(String repoName);
}
