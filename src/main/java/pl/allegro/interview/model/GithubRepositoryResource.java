package pl.allegro.interview.model;

import java.time.OffsetDateTime;

public class GithubRepositoryResource {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    private final OffsetDateTime createdAt;

    public GithubRepositoryResource(String fullName, String description, String cloneUrl, Integer stars, OffsetDateTime createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public Integer getStars() {
        return stars;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
