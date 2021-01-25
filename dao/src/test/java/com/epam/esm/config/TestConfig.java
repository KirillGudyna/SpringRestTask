package com.epam.esm.config;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.TagDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public TagDao tagDao(){
        return new TagDaoImpl();
    }

}
