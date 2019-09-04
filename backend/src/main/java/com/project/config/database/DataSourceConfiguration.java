package com.project.config.database;

import lombok.Data;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfiguration {

    private String url;
    private String dataUsername;
    private String dataPassword;
    private String defaultCatalog;

    @Bean
    DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(dataUsername);
        dataSource.setPassword(dataPassword);
        dataSource.setDefaultCatalog(defaultCatalog);

        return dataSource;
    }

}
