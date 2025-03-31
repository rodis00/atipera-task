package com.rodis00.atiperatask.model;

import java.util.List;

public class Repository {
    private String name;
    private List<RepositoryBranch> branches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RepositoryBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<RepositoryBranch> branches) {
        this.branches = branches;
    }
}
