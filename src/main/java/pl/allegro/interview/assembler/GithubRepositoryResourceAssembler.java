package pl.allegro.interview.assembler;

import org.springframework.stereotype.Component;
import pl.allegro.interview.model.GithubRepositoryResource;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

@Component
public class GithubRepositoryResourceAssembler implements Assembler<GithubRepositoryResourceExternalDto, GithubRepositoryResource> {

    @Override
    public GithubRepositoryResource toResource(GithubRepositoryResourceExternalDto externalResource) {

        return new GithubRepositoryResource(
                externalResource.getFullName(),
                externalResource.getDescription(),
                externalResource.getCloneUrl(),
                externalResource.getStars(),
                externalResource.getCreatedAt());
    }
}
