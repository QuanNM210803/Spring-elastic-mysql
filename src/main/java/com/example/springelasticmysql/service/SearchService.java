package com.example.springelasticmysql.service;

import com.example.springelasticmysql.model.UserModel;
import com.example.springelasticmysql.utils.Constants;
import com.example.springelasticmysql.utils.HelperFunctions;
import com.example.springelasticmysql.utils.ResultQuery;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchSearchPrefix;

    private static final Logger LOGGER= LoggerFactory.getLogger(SearchService.class);

    @Override
    public ResultQuery<UserModel> searchFromQuery(String query) throws IOException {
        String body= HelperFunctions.buildMultiIndexMatchBody(query);
        return executeHttpRequest(body);
    }

    private ResultQuery<UserModel> executeHttpRequest(String body) throws IOException {
        try(CloseableHttpClient httpClient= HttpClients.createDefault()){

            // tạo phuong thuc post
            ResultQuery<UserModel> resultQuery=new ResultQuery<>();
            HttpPost httpPost=new HttpPost(HelperFunctions.buildSearchUri(elasticSearchUri, "", elasticSearchSearchPrefix));
            httpPost.setHeader(Constants.CONTENT_ACCEPT, Constants.APP_TYPE);
            httpPost.setHeader(Constants.CONTENT_TYPE, Constants.APP_TYPE);

            try{
                // thuc thi post và chuyen ve phan hoi json
                httpPost.setEntity(new StringEntity(body, Constants.ENCODING_UTF8));
                HttpResponse response=httpClient.execute(httpPost);
                String message= EntityUtils.toString(response.getEntity());
                JSONObject myObject=new JSONObject(message);

                if(myObject.getJSONObject(Constants.HITS).getJSONObject(Constants.TOTAL_HITS).getInt(Constants.TOTAL_VALUES) != 0){
                    List<JSONObject> jsonObjects=new ArrayList<>();

                    myObject.getJSONObject(Constants.HITS).getJSONArray(Constants.HITS).forEach(hit->{
                        jsonObjects.add((JSONObject) hit);
                    });

                    List<UserModel> userModels=jsonObjects.stream().map(hit -> {
                        JSONObject _source=hit.getJSONObject(Constants._SOURCE);
                        return UserModel.builder()
                                .id(_source.getLong("id"))
                                .firstName(_source.getString("firstName"))
                                .lastName(_source.getString("lastName"))
                                .modificationDate(new Date(_source.getLong("modificationDate")))
                                .build();
                    }).toList();

                    resultQuery.setElements(userModels);
                    resultQuery.setNumberOfResults(myObject.getJSONObject(Constants.HITS).getJSONObject(Constants.TOTAL_HITS).getInt(Constants.TOTAL_VALUES));
                    resultQuery.setTimeTook((float) ((double)myObject.getInt(Constants.TOOK) / Constants.TO_MS) );
                }else{
                    resultQuery.setElements(new ArrayList<>());
                    resultQuery.setNumberOfResults(0);
                    resultQuery.setTimeTook((float) ((double)myObject.getInt(Constants.TOOK) / Constants.TO_MS) );
                }
            }catch (JSONException e){
                LOGGER.error("Error while connecting to elastic engine --> {}", e.getMessage());
                resultQuery.setNumberOfResults(0);
            }
            return resultQuery;
        }
    }
}
