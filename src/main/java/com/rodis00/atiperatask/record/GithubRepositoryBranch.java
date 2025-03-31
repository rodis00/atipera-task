package com.rodis00.atiperatask.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryBranch(
        String name,
        GithubCommit commit
) {
}
