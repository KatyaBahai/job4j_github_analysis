package ru.job4j.github.analysis.service.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.model.Repository;
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

    @InjectMocks
    private RepositoryServiceImpl repositoryService;

    private Repository testRepo1;
    private Repository testRepo2;

    @BeforeEach
    void setUp() {
        testRepo1 = Repository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(Instant.now())
                .language("java")
                .build();

        testRepo2 = Repository.builder()
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
        repositoryService.create(testRepo1);

        ArgumentCaptor<Repository> captor = ArgumentCaptor.forClass(Repository.class);
        verify(repositoryStore).save(captor.capture());

        Repository savedRepo = captor.getValue();
        assertThat(savedRepo).isEqualTo(testRepo1);
    }

    @Test
    void getAll() {
        repositoryService.create(testRepo1);
        repositoryService.create(testRepo2);

        when(repositoryStore.findAll()).thenReturn(List.of(testRepo1, testRepo2));

        List<Repository> result = repositoryService.getAll();

        verify(repositoryStore).findAll();
        assertThat(result).containsExactly(testRepo1, testRepo2);

    }
}