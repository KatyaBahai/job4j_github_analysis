package ru.job4j.github.analysis.service.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.model.GitHubRepository;
import ru.job4j.github.analysis.service.GitHubRemote;
import ru.job4j.github.analysis.store.RepositoryStore;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceImplTest {
    @Mock
    private RepositoryStore repositoryStore;
    @Mock
    private GitHubRemote gitHubRemote;
    @InjectMocks
    private RepositoryServiceImpl repositoryService;

    private GitHubRepository testRepo1;
    private GitHubRepository testRepo2;

    @BeforeEach
    void setUp() {
        testRepo1 = GitHubRepository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(Instant.now())
                .language("java")
                .build();

        testRepo2 = GitHubRepository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(Instant.now())
                .language("java")
                .build();
    }

    @Test
    void create() {
        when(gitHubRemote.fetchRepositories("KatyaBahai"))
                .thenReturn(List.of(testRepo1));

        repositoryService.create("KatyaBahai");

        ArgumentCaptor<GitHubRepository> captor = ArgumentCaptor.forClass(GitHubRepository.class);
        verify(repositoryStore).save(captor.capture());

        GitHubRepository savedRepo = captor.getValue();
        assertThat(savedRepo).isEqualTo(testRepo1);
    }

    @Test
    void getAll() {
        repositoryService.create("KatyaBahai");
        repositoryService.create("KatyaBahai");

        when(repositoryStore.findAll()).thenReturn(List.of(testRepo1, testRepo2));

        List<GitHubRepository> result = repositoryService.getAll();

        verify(repositoryStore).findAll();
        assertThat(result).containsExactly(testRepo1, testRepo2);

    }
}