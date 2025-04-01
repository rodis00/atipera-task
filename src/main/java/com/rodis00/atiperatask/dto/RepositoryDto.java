package com.rodis00.atiperatask.dto;

import java.util.List;

public class RepositoryDto {
    private String name;
    private List<BranchDto> branches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchDto> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDto> branches) {
        this.branches = branches;
    }
}
