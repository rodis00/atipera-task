package com.rodis00.atiperatask.model;

import com.rodis00.atiperatask.dto.RepositoryDto;

import java.util.List;

public class ApiResponse {
    private String ownerLogin;
    private List<RepositoryDto> repositories;

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<RepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        this.repositories = repositories;
    }
}
