package com.example.springelasticmysql.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Date modificationDate;
}
