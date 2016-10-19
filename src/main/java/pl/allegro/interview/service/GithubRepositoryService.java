package pl.allegro.interview.service;

import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

public interface GithubRepositoryService {

    GithubRepositoryResourceExternalDto getRepoByOwnerAndRepoName(String owner, String repoName);

}
