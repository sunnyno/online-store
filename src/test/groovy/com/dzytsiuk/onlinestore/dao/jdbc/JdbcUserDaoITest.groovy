package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.User
import org.apache.commons.dbcp.BasicDataSource
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class JdbcUserDaoITest {
    static {
        System.setProperty("properties.path", "/property/dev.application.properties")
    }
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml")

    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: -639310962, salt: "e6bbb2df-cd76-46b5-9e38-2d9bac3475fa")
        JdbcUserDao jdbcUserDao = new JdbcUserDao()
        jdbcUserDao.setDataSource(applicationContext.getBean(BasicDataSource.class))
        def actualUser = jdbcUserDao.findByLogin("zhenya") as Optional<User>
        assertTrue(actualUser.isPresent())
        assertEquals(expectedUser, actualUser.get())
    }
}
