package ru.job4j.github.analysis.service.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.GitHubRepository;
import ru.job4j.github.analysis.service.GitHubRemote;
import ru.job4j.github.analysis.store.RepositoryStore;

import java.util.List;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private RepositoryStore repositoryStore;
    @Autowired
    private GitHubRemote gitHubRemote;

    @Async
    @Override
    public void create(String userName) {
        List<GitHubRepository> repositoriesList = gitHubRemote.fetchRepositories(userName);
        repositoriesList.stream().forEach(repo -> repositoryStore.save(repo));
    }

    @Override
    public List<GitHubRepository> getAll() {
        return repositoryStore.findAll();
    }
}
