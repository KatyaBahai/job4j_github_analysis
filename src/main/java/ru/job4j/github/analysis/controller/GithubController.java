package ru.job4j.github.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.service.commit.CommitService;
import ru.job4j.github.analysis.service.repository.RepositoryService;


import java.util.List;

@RestController
@RequestMapping("/api")
public class GithubController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private CommitService commitService;
    
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/repositories")
    public List<Repository> getAllRepositories() {
        return repositoryService.getAll();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/commits/{name}")
    public List<Commit> getCommits(@PathVariable(value = "name") String repoName) {
        return commitService.getCommitsByRepository(repoName);
    }

    @PostMapping("/repository")
    public ResponseEntity<Void> create(@RequestBody Repository repository) {
        repositoryService.create(repository);
        return ResponseEntity.noContent().build();
    }
}
