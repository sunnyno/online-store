package com.dzytsiuk.onlinestore.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Profile("prod")
@Configuration
@ComponentScan(basePackages = "com.dzytsiuk.onlinestore",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern="com\\.dzytsiuk\\.onlinestore\\.web\\.controller.*"))
@PropertySource(
        value={"classpath:properties/prod.application.properties"})
public class AppProdConfig {

    @Bean
    public DataSource dataSource(
            @Value("${JDBC_DATABASE_URL}") String url,
            @Value("${JDBC_DATABASE_USERNAME}")String username,
            @Value("${JDBC_DATABASE_PASSWORD}") String password,
            @Value("${web.ssl}") String ssl,
            @Value("${web.sslfactory}") String sslFactory) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.addConnectionProperty("ssl", ssl);
        dataSource.addConnectionProperty("sslfactory", sslFactory);
        return dataSource;
    }

}
