package com.rodis00.atiperatask.service;

import com.rodis00.atiperatask.record.GithubUser;
import com.rodis00.atiperatask.record.GithubUserRepository;
import com.rodis00.atiperatask.model.ApiResponse;
import com.rodis00.atiperatask.model.Repository;
import com.rodis00.atiperatask.model.RepositoryBranch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    private final GithubService githubService;

    public ApiService(GithubService githubService) {
        this.githubService = githubService;
    }

    public ApiResponse getUser(String username) {
        GithubUser user = githubService.getGithubUser(username);

        List<GithubUserRepository> userRepositories = githubService
                .getGithubUserRepository(user.reposUrl(), false);

        List<Repository> repositories = userRepositories.stream()
                .map(r -> {
                    Repository repository = new Repository();
                    repository.setName(r.name());
                    repository.setBranches(getBranches(user.login(), r.name()));
                    return repository;
                }).toList();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setOwnerLogin(user.login());
        apiResponse.setRepositories(repositories);

        return apiResponse;
    }

    private List<RepositoryBranch> getBranches(String owner, String repositoryName) {
        return githubService.getGithubUserRepositoryBranch(owner, repositoryName).stream()
                .map(b -> {
                    RepositoryBranch branch = new RepositoryBranch();
                    branch.setName(b.name());
                    branch.setSha(b.commit().sha());
                    return branch;
                }).toList();

    }
}
