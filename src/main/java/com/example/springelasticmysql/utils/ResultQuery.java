package com.example.springelasticmysql.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultQuery<T> {
    private Float timeTook;
    private Integer numberOfResults;
    private List<T> elements;

}
