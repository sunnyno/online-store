package com.dzytsiuk.onlinestore.dao.jdbc.mapper

import com.dzytsiuk.onlinestore.entity.User
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertEquals

class UserRowMapperTest {
    @Test
    void mapRowTest() {
        def rsMock = [next     : {true},
                      getString: { password -> "pass" }] as ResultSet
        User expectedUser = new User(login: 'zhenya', password: "pass")
        assertEquals(expectedUser, new UserRowMapper().mapRow(rsMock, "zhenya"))
    }
}
