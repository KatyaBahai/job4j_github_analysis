package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.service.commit.CommitService;
import ru.job4j.github.analysis.service.repository.RepositoryService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class TaskScheduler {
    private RepositoryService repositoryService;
    private CommitService commitService;
    private GitHubRemote gitHubRemote;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        List<Repository> repositories = repositoryService.getAll();

        repositories.forEach(repo -> {
            Optional<Commit> latestCommit = commitService.findLatestCommit(repo);
            Optional<String> since = latestCommit.map(commit -> commit.getDate().toString());
            List<CommitDto> commitDtos = gitHubRemote.fetchCommits(repo.getFullName(), since);
            commitService.saveCommits(commitDtos, repo);
        });
    }
}
