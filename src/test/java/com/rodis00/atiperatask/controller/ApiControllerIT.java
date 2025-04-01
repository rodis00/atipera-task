package com.rodis00.atiperatask.controller;

import com.rodis00.atiperatask.dto.BranchDto;
import com.rodis00.atiperatask.dto.RepositoryDto;
import com.rodis00.atiperatask.exception.GithubUserNotFoundException;
import com.rodis00.atiperatask.model.ApiResponse;
import com.rodis00.atiperatask.service.ApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApiService apiService;

    @Test
    void shouldReturnGithubUser() throws Exception {
        String username = "test";

        BranchDto branchDto = new BranchDto();
        branchDto.setName("branch-1");
        branchDto.setSha("emaple-sha-code");

        RepositoryDto repositoryDto = new RepositoryDto();
        repositoryDto.setName("repo-1");
        repositoryDto.setBranches(List.of(branchDto));

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setOwnerLogin(username);
        apiResponse.setRepositories(List.of(repositoryDto));

        when(apiService.findGithubUser(username))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/github/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerLogin").value(username))
                .andExpect(jsonPath("$.repositories[0].name").value(repositoryDto.getName()))
                .andExpect(jsonPath("$.repositories[0].branches[0].name").value(branchDto.getName()))
                .andExpect(jsonPath("$.repositories[0].branches[0].sha").value(branchDto.getSha()));
    }

    @Test
    void shouldThrowExceptionWhenGithubUserNotFound() throws Exception {
        String username = "test";

        when(apiService.findGithubUser(username))
                .thenThrow(new GithubUserNotFoundException("User not found"));

        mockMvc.perform(get("/api/v1/github/users/{username}", username))
                .andExpect(status().isNotFound());
    }
}