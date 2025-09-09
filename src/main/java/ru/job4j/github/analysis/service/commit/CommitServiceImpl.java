package ru.job4j.github.analysis.service.commit;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.mapper.DtoToCommitMapper;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.GitHubRepository;
import ru.job4j.github.analysis.store.CommitStore;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommitServiceImpl implements CommitService {
    @Autowired
    private DtoToCommitMapper commitMapper;
    @Autowired
    private CommitStore commitStore;

    @Override
    public void saveCommits(List<CommitDto> commitDtos, GitHubRepository repo) {
        List<CommitDto> filteredByShaNewCommitDtos = commitDtos.stream()
                .filter(dto -> !commitStore.existsBySha(dto.getSha())).toList();
        List<Commit> commits = commitMapper.toCommits(filteredByShaNewCommitDtos, repo);
        commitStore.saveAll(commits);
    }

    @Override
    public List<Commit> getCommitsByRepository(String repoName) {
        return commitStore.findByGitHubRepositoryFullName(repoName);
    }

    @Override
    public Optional<Commit> findLatestCommit(GitHubRepository repo) {
        return commitStore.findByGitHubRepositoryFullName(repo.getFullName())
                .stream()
                .max(Comparator.comparing(Commit::getDate));
    }
}
