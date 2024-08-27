package com.pivotree.appzone.appconfig;///*
// * (c) 2023 Pivotree |  All rights reserved
// */
//package com.example.backend_mini_project.appconfig;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.text.SimpleDateFormat;
//
//
///**
// * {@link ObjectMapper} bean is configured here.
// */
//
//@Configuration
//public class ObjectMapperConfig {
//
//    /**
//     * {@link ObjectMapper} Bean.
//     *
//     * @return {@link ObjectMapper} is the instance of the object mapper.
//     */
//    @Bean("customObjectMapper")
//    ObjectMapper shipMSObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//        return objectMapper;
//    }
//
//    /**
//     * Default bean to be used by spring to deserialize API JSON to inTemplate objects
//     *
//     * @return {@link ObjectMapper} is the instance of the object mapper.
//     */
//    @Primary
//    public ObjectMapper defaultObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
//        return objectMapper;
//    }
//}
//

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
