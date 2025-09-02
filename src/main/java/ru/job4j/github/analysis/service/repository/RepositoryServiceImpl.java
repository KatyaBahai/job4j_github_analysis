package ru.job4j.github.analysis.service.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.store.RepositoryStore;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class RepositoryServiceImpl implements RepositoryService {
    private RepositoryStore repositoryStore;

    @Async
    @Override
    public void create(Repository repository) {
        repositoryStore.save(repository);
    }

    @Override
    public List<Repository> getAll() {
        return repositoryStore.findAll();
    }
}
