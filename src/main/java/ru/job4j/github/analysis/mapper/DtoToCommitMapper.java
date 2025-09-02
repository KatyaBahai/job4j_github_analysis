package ru.job4j.github.analysis.mapper;


import lombok.NoArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

@Mapper(componentModel = "spring")
@NoArgsConstructor
public abstract class DtoToCommitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "repository", source = "repository")
    public abstract Commit toCommit(CommitDto dto, Repository repository);

    public List<Commit> toCommits(List<CommitDto> dtos, @Context Repository repository) {
        return dtos.stream()
                .map(dto -> toCommit(dto, repository))
                .toList();
    }
}
