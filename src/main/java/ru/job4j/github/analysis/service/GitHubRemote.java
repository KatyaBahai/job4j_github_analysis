package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.job4j.github.analysis.dto.CommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRemote {
    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> fetchRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<List<Repository>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Repository>>() { });
        return response.getBody();
    }

    public List<CommitDto> fetchCommits(String repoName, Optional<String> since) {
        String baseUrl = "https://api.github.com/repos/" + repoName + "/commits";
       UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
       since.ifPresent(s -> uriBuilder.queryParam("since", s));
        ResponseEntity<List<CommitDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CommitDto>>() { });
        return response.getBody();
    }
}
