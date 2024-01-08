package com.example.chat.controller;

import com.example.chat.utils.exception.ErrorServerException;
import com.example.chat.model.HashtagsGroup;
import com.example.chat.model.User;
import com.example.chat.servise.impls.HashtagGroupService;
import com.example.chat.servise.impls.UserServiceImpl;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class HashtagGroupController {

    private final HashtagGroupService hashtagGroupService;

    private final UserServiceImpl userService;


    @Operation(summary = "User onboarding - step: hashtags")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user-onboarding/hashtags-group")
    @JsonView(JsonViews.ViewFieldHashtagsGroups.class)
    public ResponseEntity<List<HashtagsGroup>> allHashtagGroupsUserLocale() throws ErrorServerException {
        User user = userService.getUserFromSecurityContextHolder();
        return ResponseEntity.ok(hashtagGroupService.allHashtagsGroupUserLocale(user));
    }
}
