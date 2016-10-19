package pl.allegro.interview.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.allegro.interview.util.DateFormatUtil;

import java.util.Date;

public class GithubRepositoryResource {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatUtil.ISO_8601)
    private final Date createdAt;

    public GithubRepositoryResource(String fullName, String description, String cloneUrl, Integer stars, Date createdAt) {
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

    public Date getCreatedAt() {
        return createdAt;
    }
}
