package ru.job4j.github.analysis.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.github.analysis.model.GitHubRepository;

@Repository
public interface RepositoryStore extends JpaRepository<GitHubRepository, Long> {
    GitHubRepository findByFullName(String repoName);
}
