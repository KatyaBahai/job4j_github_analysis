package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.GitHubRepository;
import ru.job4j.github.analysis.service.commit.CommitService;
import ru.job4j.github.analysis.service.repository.RepositoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomTaskScheduler {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private CommitService commitService;
    @Autowired
    private GitHubRemote gitHubRemote;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        List<GitHubRepository> repositories = repositoryService.getAll();

        repositories.forEach(repo -> {
            Optional<Commit> latestCommit = commitService.findLatestCommit(repo);
            Optional<String> since = latestCommit.map(commit -> commit.getDate().toString());
            List<CommitDto> commitDtos = gitHubRemote.fetchCommits(repo.getFullName(), since);
            commitService.saveCommits(commitDtos, repo);
        });
    }
}
