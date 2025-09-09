package ru.job4j.github.analysis.service.repository;

import ru.job4j.github.analysis.model.GitHubRepository;

import java.util.List;

public interface RepositoryService {
    void create(String userName);

    List<GitHubRepository> getAll();
}
