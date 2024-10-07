package com.example.springelasticmysql.controller;

import com.example.springelasticmysql.service.ISearchService;
import com.example.springelasticmysql.utils.Constants;
import com.example.springelasticmysql.utils.PathResources;
import com.example.springelasticmysql.utils.ResultQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(PathResources.SEARCH_CONTROLLER)
public class SearchController {
    private final ISearchService searchService;

    @GetMapping(Constants.SEARCH_QUERY + "/{" + Constants.QUERY +"}")
    public ResponseEntity<ResultQuery> searchQuery(@PathVariable String query) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchFromQuery(query.trim().toLowerCase()));
    }
}
