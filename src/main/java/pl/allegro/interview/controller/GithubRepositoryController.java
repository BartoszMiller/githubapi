package pl.allegro.interview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.interview.assembler.Assembler;
import pl.allegro.interview.model.GithubRepositoryResource;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;
import pl.allegro.interview.service.GithubRepositoryService;

@RestController
@RequestMapping("/repositories")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryServiceRest;
    private final Assembler<GithubRepositoryResourceExternalDto, GithubRepositoryResource> githubRepositoryResourceAssembler;

    public GithubRepositoryController(GithubRepositoryService githubRepositoryServiceRest,
                                      Assembler<GithubRepositoryResourceExternalDto, GithubRepositoryResource> githubRepositoryResourceAssembler) {
        this.githubRepositoryServiceRest = githubRepositoryServiceRest;
        this.githubRepositoryResourceAssembler = githubRepositoryResourceAssembler;
    }

    @RequestMapping("/{owner}/{repository-name}")
    public ResponseEntity<GithubRepositoryResource> getRepoByOwnerAndRepoName(@PathVariable("owner") final String owner,
                                                                              @PathVariable("repository-name") final String repoName) {
        GithubRepositoryResourceExternalDto repositoryResourceExternal = githubRepositoryServiceRest.getRepoByOwnerAndRepoName(owner, repoName);
        GithubRepositoryResource githubRepositoryResource = githubRepositoryResourceAssembler.toResource(repositoryResourceExternal);
        return ResponseEntity.ok(githubRepositoryResource);
    }
}
