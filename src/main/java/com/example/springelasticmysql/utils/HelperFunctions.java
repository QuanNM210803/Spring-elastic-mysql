package com.example.springelasticmysql.utils;

import org.json.JSONArray;

import java.util.Arrays;

public class HelperFunctions {

    private static final String[] USER_FIELDS={"firstName", "lastName"};

    public static String buildMultiIndexMatchBody(String query){
        return "{\n" +
                "\"from\": 0,\n" +
                "\"size\": 100,\n" +
                "\"track_total_hits\": true,\n" +
                "\"sort\" : {\n" +
                "      \"id\": {\"order\": \"asc\"}\n" +
                "      },\n" +
                "  \"query\": {\n" +
                "    \"query_string\" : {\n" +
                "      \"query\":      \"*" + query + "*\",\n" +
                "      \"fields\":     " + new JSONArray(Arrays.asList(USER_FIELDS)) + ",\n" +
                "      \"default_operator\": \"AND\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"highlight\": {\n" +
                "    \"fields\": {\n" +
                "      \"*\": {}\n" +
                "    },\n" +
                "    \"require_field_match\": true\n" +
                " }\n" +
                "}";

//        {
//            "from": 0,
//            "size": 100,
//            "track_total_hits": true,
//            "sort": {
//                "id": {
//                    "order": "asc"
//                }
//            },
//            "query": {
//                "query_string": {
//                    "query": "*your_query_here*",
//                    "fields": ["field1", "field2", "field3"],  // Thay thế bằng USER_FIELDS cụ thể
//                    "default_operator": "AND"
//                }
//            },
//            "highlight": {
//                "fields": {
//                    "*": {}
//                },
//                "require_field_match": true
//            }
//        }

    }

    public static String buildSearchUri(String elasticSearchUri, String elasticSearchIndex, String elasticSearchSearch){
        return elasticSearchUri + elasticSearchIndex + elasticSearchSearch;
    }
}
