package pl.allegro.interview.service.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

import java.util.Collections;

@Component
public class GithubApiClient {

    private final RestTemplate restTemplate;
    private final String githubApiUrlRoot;
    private final String githubApiUrlRepos;
    private final String githubApiVersion;

    public GithubApiClient(RestTemplate restTemplate,
                           @Value("${github.api.url.root}") String githubApiUrlRoot,
                           @Value("${github.api.url.repos}") String githubApiUrlRepos,
                           @Value("${github.api.version}") String githubApiVersion) {
        this.restTemplate = restTemplate;
        this.githubApiUrlRoot = githubApiUrlRoot;
        this.githubApiUrlRepos = githubApiUrlRepos;
        this.githubApiVersion = githubApiVersion;
    }

    public GithubRepositoryResourceExternalDto getRepoByOwnerAndRepoName(String owner, String repoName) {

        String uri = buildGithubRepositoryUri(owner, repoName);
        HttpHeaders headers = buildHeaders();
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Void>(headers), GithubRepositoryResourceExternalDto.class).getBody();
    }

    private HttpHeaders buildHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.valueOf(githubApiVersion)));
        return headers;
    }

    private String buildGithubRepositoryUri(String owner, String repoName) {

        return UriComponentsBuilder
                .fromHttpUrl(githubApiUrlRoot)
                .pathSegment(githubApiUrlRepos, owner, repoName)
                .build()
                .toUri()
                .toString();
    }
}
