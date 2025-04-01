package com.rodis00.atiperatask.service;

import com.rodis00.atiperatask.exception.GithubUserNotFoundException;
import com.rodis00.atiperatask.record.Branch;
import com.rodis00.atiperatask.record.User;
import com.rodis00.atiperatask.record.Repository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GithubService {

    private final RestClient restClient;

    public GithubService() {
        restClient = RestClient.builder()
                .baseUrl("https://api.github.com/")
                .build();
    }

    public User getGithubUser(String username) {
        return restClient.get()
                .uri("users/{username}", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new GithubUserNotFoundException("Github user not found");
                })
                .body(User.class);
    }

    public List<Repository> getGithubUserRepository(
            String username,
            boolean fork
    ) {
        Repository[] repositories = restClient.get()
                .uri("users/{username}/repos", username)
                .retrieve()
                .body(Repository[].class);

        List<Repository> userRepos = new ArrayList<>();

        if (Objects.nonNull(repositories)) {
            userRepos.addAll(Arrays.asList(repositories));
        }

        if (fork) {
            return userRepos.stream()
                    .filter(r -> r.fork().equals(true))
                    .toList();
        }

        return userRepos;
    }

    public List<Branch> getGithubUserRepositoryBranch(String owner, String repositoryName) {
        String uri = "repos/" + owner + "/" + repositoryName + "/branches";

        Branch[] branches = restClient.get()
                .uri(uri)
                .retrieve()
                .body(Branch[].class);

        List<Branch> repoBranches = new ArrayList<>();

        if (Objects.nonNull(branches)) {
            repoBranches.addAll(Arrays.asList(branches));
        }

        return repoBranches;
    }
}
