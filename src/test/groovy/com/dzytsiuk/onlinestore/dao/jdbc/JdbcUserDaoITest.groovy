package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.ioc.context.ApplicationContext
import com.dzytsiuk.ioc.context.ClassPathApplicationContext
import com.dzytsiuk.onlinestore.entity.User
import org.apache.commons.dbcp.BasicDataSource
import org.junit.Test

import static org.junit.Assert.assertEquals

class JdbcUserDaoITest {
    String contextFile = ClassLoader.getSystemClassLoader().getResource("context.xml").getPath();
    ApplicationContext applicationContext = new ClassPathApplicationContext(contextFile);

    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: "-703043761")
        System.setProperty("properties", "dev.application.properties")
        JdbcUserDao jdbcUserDao = new JdbcUserDao()
        jdbcUserDao.setDataSource(applicationContext.getBean(BasicDataSource.class))
        def actualUser = jdbcUserDao.findByLogin("zhenya") as User
        assertEquals(expectedUser, actualUser)
    }
}
