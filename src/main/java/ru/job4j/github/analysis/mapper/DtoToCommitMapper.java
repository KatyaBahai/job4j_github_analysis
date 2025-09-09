package ru.job4j.github.analysis.mapper;


import lombok.NoArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.GitHubRepository;

import java.util.List;

@Mapper(componentModel = "spring")
@NoArgsConstructor
public abstract class DtoToCommitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gitHubRepository", source = "gitHubRepository")
    @Mapping(target = "author", source = "dto.commit.author.name")
    @Mapping(target = "message", source = "dto.commit.message")
    @Mapping(target = "date", source = "dto.commit.author.date")
    public abstract Commit toCommit(CommitDto dto, GitHubRepository gitHubRepository);

    public List<Commit> toCommits(List<CommitDto> dtos, @Context GitHubRepository repository) {
        return dtos.stream()
                .map(dto -> toCommit(dto, repository))
                .toList();
    }
}
