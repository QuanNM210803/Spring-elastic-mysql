package com.example.springelasticmysql.service;

import com.example.springelasticmysql.utils.ResultQuery;

import java.io.IOException;

public interface ISearchService {
    ResultQuery searchFromQuery(String query) throws IOException;
}
