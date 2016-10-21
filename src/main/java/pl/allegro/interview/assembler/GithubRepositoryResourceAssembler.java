package pl.allegro.interview.assembler;

import org.springframework.stereotype.Component;
import pl.allegro.interview.model.GithubRepositoryResource;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class GithubRepositoryResourceAssembler implements Assembler<GithubRepositoryResourceExternalDto, GithubRepositoryResource> {

    @Override
    public GithubRepositoryResource toResource(GithubRepositoryResourceExternalDto externalResource) {

        return new GithubRepositoryResource(
                externalResource.getFullName(),
                externalResource.getDescription(),
                externalResource.getCloneUrl(),
                externalResource.getStars(),
                toLocalDateTime(externalResource.getCreatedAt()));
    }

    private LocalDateTime toLocalDateTime(ZonedDateTime createdAt) {

        if (createdAt != null) {
            return LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
        } else {
            return null;
        }
    }
}
