package pl.allegro.interview.service;

import org.springframework.stereotype.Service;
import pl.allegro.interview.service.remote.GithubApiClient;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

@Service
public class GithubRepositoryServiceRest implements GithubRepositoryService {

    private final GithubApiClient githubApiClient;

    public GithubRepositoryServiceRest(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    @Override
    public GithubRepositoryResourceExternalDto getRepoByOwnerAndRepoName(String owner, String repoName) {
        return githubApiClient.getRepoByOwnerAndRepoName(owner, repoName);
    }
}
