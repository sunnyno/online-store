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
    @Value("${JDBC_DATABASE_URL}")
    private String url;
    @Value("${JDBC_DATABASE_USERNAME}")
    private String username;
    @Value("${JDBC_DATABASE_PASSWORD}")
    private String password;
    @Value("${web.ssl}")
    private String ssl;
    @Value("${web.sslfactory}")
    private String sslFactory;


    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.addConnectionProperty("ssl", ssl);
        dataSource.addConnectionProperty("sslfactory", sslFactory);
        return dataSource;
    }

}
