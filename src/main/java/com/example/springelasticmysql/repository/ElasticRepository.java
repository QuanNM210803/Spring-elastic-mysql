package com.example.springelasticmysql.repository;

import com.example.springelasticmysql.model.UserModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<UserModel, Long> {
}
