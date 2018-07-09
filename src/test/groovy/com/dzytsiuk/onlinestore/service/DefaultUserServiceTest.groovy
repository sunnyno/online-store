package com.dzytsiuk.onlinestore.service

import com.dzytsiuk.onlinestore.dao.ProductDao
import com.dzytsiuk.onlinestore.dao.UserDao
import com.dzytsiuk.onlinestore.entity.User
import org.junit.Test

import static org.junit.Assert.assertEquals

class DefaultUserServiceTest {
    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: "-703043761")
        def rsUserDao = { findByLogin -> expectedUser } as UserDao

        UserService userService = new DefaultUserService(userDao: rsUserDao)
        def actualUser = userService.findByLogin("zhenya")
        assertEquals(expectedUser, actualUser)
    }
}
