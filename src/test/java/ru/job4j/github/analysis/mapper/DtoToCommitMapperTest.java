package ru.job4j.github.analysis.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@NoArgsConstructor
class DtoToCommitMapperTest {
    private final DtoToCommitMapper mapper = Mappers.getMapper(DtoToCommitMapper.class);

    @Test
    void whenToCommitAllFieldsMappedCorrectly() {
        Instant date = Instant.now();
        Repository testRepo = Repository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(date.minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(date)
                .language("java")
                .build();
        CommitDto commitDto = new CommitDto("sha123", "Initial commit", "Artem", date);

        Commit commit = mapper.toCommit(commitDto, testRepo);

        assertThat(commit.getId()).isNull();
        assertThat(commit.getSha()).isEqualTo("sha123");
        assertThat(commit.getMessage()).isEqualTo("Initial commit");
        assertThat(commit.getDate()).isEqualTo(date);
        assertThat(commit.getRepository()).isEqualTo(testRepo);
    }

    @Test
    void whenToCommitsAllFieldsMappedCorrectly() {
        Instant date = Instant.now();
        Repository testRepo = Repository.builder()
                .id(1L)
                .fullName("katya/analysis")
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .htmlUrl("https://github.com/katya/analysis")
                .updatedAt(Instant.now())
                .language("java")
                .build();
        CommitDto commitDto = new CommitDto("sha123", "Initial commit", "Artem", date);
        CommitDto commitDto2 = new CommitDto("sha2", "Second commit", "Boris", date);
        List<CommitDto> dtos = List.of(commitDto, commitDto2);

        List<Commit> commits = mapper.toCommits(dtos, testRepo);

        assertThat(commits).hasSize(2);
        assertThat(commits)
                .extracting(Commit::getSha)
                .containsExactly("sha123", "sha2");

        assertThat(commits)
                .extracting(Commit::getRepository)
                .containsOnly(testRepo);
    }
}