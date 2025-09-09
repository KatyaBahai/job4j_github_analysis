package ru.job4j.github.analysis.service.commit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.mapper.DtoToCommitMapper;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.GitHubRepository;
import ru.job4j.github.analysis.store.CommitStore;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommitServiceImplTest {
    @Mock
    private DtoToCommitMapper commitMapper;

    @Mock
    private CommitStore commitStore;

    @InjectMocks
    private CommitServiceImpl commitService;

    private GitHubRepository testRepo;
    private CommitDto commitDto1;
    private CommitDto commitDto2;
    private Commit commit1;
    private Commit commit2;

    @BeforeEach
    void setUp() {
        testRepo = GitHubRepository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(Instant.now())
                .language("java")
                .build();

        commitDto1 = new CommitDto("sha1", "First commit", "Artem", Instant.now().minus(Duration.ofDays(1)));
        commitDto2 = new CommitDto("sha2", "Second commit", "Boris", Instant.now());

        commit1 = Commit.builder()
                .sha("sha1")
                .date(commitDto1.getCommit().getAuthor().getDate())
                .author(commitDto1.getCommit().getAuthor().getName())
                .message(commitDto1.getCommit().getMessage())
                .gitHubRepository(testRepo)
                .build();

        commit2 = Commit.builder()
                .sha("sha2")
                .date(commitDto2.getCommit().getAuthor().getDate())
                .author(commitDto2.getCommit().getAuthor().getName())
                .message(commitDto2.getCommit().getMessage())
                .gitHubRepository(testRepo)
                .build();
    }

    @Test
    void whenSaveCommitsFiltersExistingAndSavesNew() {
        when(commitStore.existsBySha("sha1")).thenReturn(true);
        when(commitStore.existsBySha("sha2")).thenReturn(false);
        when(commitMapper.toCommits(List.of(commitDto2), testRepo)).thenReturn(List.of(commit2));

        commitService.saveCommits(List.of(commitDto1, commitDto2), testRepo);

        ArgumentCaptor<List<Commit>> captor = ArgumentCaptor.forClass(List.class);

        verify(commitStore).saveAll(captor.capture());

        List<Commit> savedCommits = captor.getValue();
        assertThat(savedCommits).containsExactly(commit2);
    }

    @Test
    void whenGetCommitsByRepositoryReturnCommits() {
        when(commitStore.findByGitHubRepositoryFullName("katya/analysis"))
                .thenReturn(List.of(commit1, commit2));

        List<Commit> result = commitService.getCommitsByRepository("katya/analysis");

        assertThat(result).containsExactly(commit1, commit2);
        verify(commitStore).findByGitHubRepositoryFullName("katya/analysis");
    }

    @Test
    void findLatestCommitShouldReturnNewestCommit() {
        when(commitStore.findByGitHubRepositoryFullName("katya/analysis"))
                .thenReturn(List.of(commit1, commit2));

        Optional<Commit> result = commitService.findLatestCommit(testRepo);

        verify(commitStore).findByGitHubRepositoryFullName("katya/analysis");
        assertThat(result).isPresent();
        assertThat(result.get().getSha()).isEqualTo("sha2");

    }

    @Test
    void findLatestCommitWhenNoCommitsThenEmpty() {
        when(commitStore.findByGitHubRepositoryFullName("katya/analysis"))
                .thenReturn(List.of());

        Optional<Commit> result = commitService.findLatestCommit(testRepo);

        assertThat(result).isEmpty();
    }
}