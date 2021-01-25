package com.epam.esm.model.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
@EnableWebMvc
public class DevDataSource  implements WebMvcConfigurer {
    private static final String PROPERTIES_FILENAME = "jdbc.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(PROPERTIES_FILENAME);
        return new HikariDataSource(config);
    }
}
