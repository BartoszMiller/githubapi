package pl.allegro.interview.model.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class GithubRepositoryResourceExternalDto {

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private ZonedDateTime createdAt;

    public GithubRepositoryResourceExternalDto() {
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("clone_url")
    public String getCloneUrl() {
        return cloneUrl;
    }

    @JsonProperty("stargazers_count")
    public Integer getStars() {
        return stars;
    }

    @JsonProperty("created_at")
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
