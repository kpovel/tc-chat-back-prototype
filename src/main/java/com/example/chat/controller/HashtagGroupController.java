package com.example.chat.controller;

import com.example.chat.utils.exception.ErrorServerException;
import com.example.chat.model.HashtagsGroup;
import com.example.chat.servise.impls.HashtagGroupService;
import com.example.chat.utils.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class HashtagGroupController {

    private final HashtagGroupService hashtagGroupService;

    @GetMapping("/hashtags-group/all-hashtags-locale")
    @Operation(summary = "Get all hashtags by locale", description = "Required parameter for local download hashtags - ?lang=uk or ?lang=en")
    @SecurityRequirement(name = "Bearer Authentication")
    @JsonView(JsonViews.ViewFieldUUIDHashtagsGroups.class)
    public ResponseEntity<List<HashtagsGroup>> getAllHashtagGroupsUserLocale() throws ErrorServerException {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return ResponseEntity.ok(hashtagGroupService.getAllHashtagsGroupsByLocale(currentLocale.getLanguage()));
    }
}
