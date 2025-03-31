package com.rodis00.atiperatask.service;

import com.rodis00.atiperatask.exception.GithubUserNotFoundException;
import com.rodis00.atiperatask.record.GithubRepositoryBranch;
import com.rodis00.atiperatask.record.GithubUser;
import com.rodis00.atiperatask.record.GithubUserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GithubService {

    public GithubUser getGithubUser(String username) {
        try {
            String url = "https://api.github.com/users/" + username;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GithubUser> response = restTemplate.getForEntity(url, GithubUser.class);

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GithubUserNotFoundException("Github user not found");
            }
            throw new RuntimeException("Failed to fetch GitHub user: " + e.getMessage(), e);
        }
    }

    public List<GithubUserRepository> getGithubUserRepository(
            String repositoryUrl,
            boolean fork
    ) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GithubUserRepository[]> response = restTemplate
                .getForEntity(repositoryUrl, GithubUserRepository[].class);

        List<GithubUserRepository> userRepos = new ArrayList<>();

        if (Objects.nonNull(response.getBody())) {
            userRepos.addAll(Arrays.asList(response.getBody()));
        }

        if (fork) {
            return userRepos.stream()
                    .filter(r -> r.fork().equals(true))
                    .toList();
        }

        return userRepos;
    }

    public List<GithubRepositoryBranch> getGithubUserRepositoryBranch(String owner, String repositoryName) {
        String url = "https://api.github.com/repos/" + owner + "/" + repositoryName + "/branches";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GithubRepositoryBranch[]> response = restTemplate
                .getForEntity(url, GithubRepositoryBranch[].class);

        List<GithubRepositoryBranch> repoBranches = new ArrayList<>();

        if (Objects.nonNull(response.getBody())) {
            repoBranches.addAll(Arrays.asList(response.getBody()));
        }

        return repoBranches;
    }
}
