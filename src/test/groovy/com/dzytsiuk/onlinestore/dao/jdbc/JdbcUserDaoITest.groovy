package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.User
import org.junit.Test

import static org.junit.Assert.assertEquals

class JdbcUserDaoITest {
    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: "-703043761")
        System.setProperty("properties", "dev.application.properties")
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DataSourceManager().getDataSource())
        def actualUser = jdbcUserDao.findByLogin("zhenya") as User
        assertEquals(expectedUser, actualUser)
    }
}
