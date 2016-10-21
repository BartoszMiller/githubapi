package pl.allegro.interview.model;

import java.time.LocalDateTime;

public class GithubRepositoryResource {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    private final LocalDateTime createdAt;

    public GithubRepositoryResource(String fullName, String description, String cloneUrl, Integer stars, LocalDateTime createdAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
