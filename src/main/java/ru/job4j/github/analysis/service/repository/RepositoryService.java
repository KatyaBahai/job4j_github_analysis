package ru.job4j.github.analysis.service.repository;

import ru.job4j.github.analysis.model.Repository;

import java.util.List;

public interface RepositoryService {
    void create(Repository repository);

    List<Repository> getAll();
}
