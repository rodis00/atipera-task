package com.rodis00.atiperatask.controller;

import com.rodis00.atiperatask.service.ApiService;
import com.rodis00.atiperatask.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("github/users/{username}")
    public ResponseEntity<ApiResponse> findGithubUser(@PathVariable String username) {
        return ResponseEntity.ok(apiService.findGithubUser(username));
    }
}
