package com.rodis00.atiperatask.service;

import com.rodis00.atiperatask.dto.BranchDto;
import com.rodis00.atiperatask.dto.RepositoryDto;
import com.rodis00.atiperatask.dto.UserDto;
import com.rodis00.atiperatask.model.ApiResponse;
import com.rodis00.atiperatask.record.Branch;
import com.rodis00.atiperatask.record.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    private final GithubService githubService;

    public ApiService(GithubService githubService) {
        this.githubService = githubService;
    }

    public ApiResponse findGithubUser(String username) {
        UserDto user = createUserDto(username);
        List<RepositoryDto> repositoryDtoList = getRepositoryDtoList(username);

        return createApiResponse(user, repositoryDtoList);
    }

    private ApiResponse createApiResponse(
            UserDto user,
            List<RepositoryDto> repositoryDtoList
    ) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setOwnerLogin(user.getUsername());
        apiResponse.setRepositories(repositoryDtoList);

        return apiResponse;
    }

    private UserDto createUserDto(String username) {
        User user = githubService.getGithubUser(username);
        UserDto userDto = new UserDto();
        userDto.setUsername(user.login());

        return userDto;
    }

    private List<RepositoryDto> getRepositoryDtoList(String username) {
        return githubService.getGithubUserRepository(username, false).stream()
                .map(repository -> {
                    RepositoryDto repositoryDto = createRepositoryDto(username, repository.name());
                    return repositoryDto;
                }).toList();
    }

    private RepositoryDto createRepositoryDto(
            String username,
            String repositoryName
    ) {
        RepositoryDto repositoryDto = new RepositoryDto();
        repositoryDto.setName(repositoryName);
        repositoryDto.setBranches(getBranchListDto(username, repositoryName));

        return repositoryDto;
    }

    private List<BranchDto> getBranchListDto(
            String owner,
            String repositoryName
    ) {
        return githubService.getGithubUserRepositoryBranch(owner, repositoryName).stream()
                .map(branch -> {
                    BranchDto branchDto = createBranchDto(branch);
                    return branchDto;
                }).toList();

    }

    private BranchDto createBranchDto(Branch branch) {
        BranchDto branchDto = new BranchDto();
        branchDto.setName(branch.name());
        branchDto.setSha(branch.commit().sha());
        return branchDto;
    }
}
