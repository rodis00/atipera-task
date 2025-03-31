package com.rodis00.atiperatask.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubUser(
        String login,
        @JsonProperty("repos_url")
        String reposUrl
) {
}
