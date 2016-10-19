package pl.allegro.interview.service.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

import java.net.URI;

@Component
public class GithubApiClient {

    private final RestTemplate restTemplate;
    private final String githubUrlApi;
    private String githubUrlRepos;

    public GithubApiClient(RestTemplate restTemplate,
                           @Value("${github.url.api}") String githubUrlApi,
                           @Value("${github.url.repos}") String githubUrlRepos) {
        this.restTemplate = restTemplate;
        this.githubUrlApi = githubUrlApi;
        this.githubUrlRepos = githubUrlRepos;
    }

    public GithubRepositoryResourceExternalDto getRepoByOwnerAndRepoName(String owner, String repoName) {
        String uri = buildGithubRepositoryUri(owner, repoName);
        ResponseEntity<GithubRepositoryResourceExternalDto> forEntity = restTemplate.getForEntity(uri, GithubRepositoryResourceExternalDto.class);
        return forEntity.getBody();
    }

    private String buildGithubRepositoryUri(String owner, String repoName) {
        return UriComponentsBuilder
                .fromHttpUrl(githubUrlApi)
                .pathSegment(githubUrlRepos, owner, repoName)
                .build()
                .toUri()
                .toString();
    }
}
