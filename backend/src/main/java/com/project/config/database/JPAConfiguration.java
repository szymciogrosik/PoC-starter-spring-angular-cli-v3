package com.project.config.database;

import com.project.App;
import lombok.Data;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.jpa.hibernate")
public class JPAConfiguration
{
    private String showSql;
    private String dialect;
    private String implicitNamingStrategy;
    private String formatSql;
    private String globallyQuotedIdentifiers;
    private String ddlAuto;


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
    {
        Properties jpaProperties = new Properties();

        jpaProperties.setProperty("hibernate.dialect", dialect);
        jpaProperties.setProperty("hibernate.show_sql", showSql);
        jpaProperties.setProperty("hibernate.format_sql", formatSql);
        jpaProperties.setProperty("hibernate.implicit_naming_strategy", implicitNamingStrategy);
        jpaProperties.setProperty("hibernate.globally_quoted_identifiers", globallyQuotedIdentifiers);
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
        localContainerEntityManagerFactoryBean.setPackagesToScan(App.class.getPackage().getName());
        localContainerEntityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

