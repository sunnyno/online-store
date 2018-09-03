package com.dzytsiuk.onlinestore.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Profile({"dev", "default"})
@Configuration
@ComponentScan(basePackages = "com.dzytsiuk.onlinestore",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "com\\.dzytsiuk\\.onlinestore\\.web\\.controller.*"))
@PropertySource(
        value = {"classpath:properties/dev.application.properties"})
public class AppDevConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Bean
    public BasicDataSource dataSource(@Value("${db.url}") String url
            , @Value("${db.username}") String username
            , @Value("${db.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        logger.info("Datasource configured");
        return dataSource;
    }
}
