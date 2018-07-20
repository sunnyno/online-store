package com.dzytsiuk.onlinestore.service

import com.dzytsiuk.onlinestore.dao.ProductDao
import com.dzytsiuk.onlinestore.dao.UserDao
import com.dzytsiuk.onlinestore.entity.User
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class DefaultUserServiceTest {
    @Test
    void findByLoginTest() {
        def expectedUser = Optional.of(new User(login: 'zhenya', password: -703043761))
        def rsUserDao = { findByLogin -> expectedUser } as UserDao

        UserService userService = new DefaultUserService(userDao: rsUserDao)
        Optional<User> actualUser = userService.findByLogin("zhenya")
        assertTrue(actualUser.isPresent())
        assertEquals(expectedUser.get(), actualUser.get())
    }
}
