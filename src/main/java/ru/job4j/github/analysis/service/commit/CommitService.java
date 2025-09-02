package ru.job4j.github.analysis.service.commit;

import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;
import java.util.Optional;

public interface CommitService {
    void saveCommits(List<CommitDto> commitDtos, Repository repo);

    public List<Commit> getCommitsByRepository(String repoName);

    Optional<Commit> findLatestCommit(Repository repo);
}
