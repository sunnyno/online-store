package com.dzytsiuk.onlinestore.dao.jdbc.mapper

import com.dzytsiuk.onlinestore.entity.User
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertEquals

class UserRowMapperTest {
    @Test
    void mapRowTest() {
        def rsMock = [next     : { true },
                      getInt   : { password -> 1 },
                      getString: { str -> "str" }] as ResultSet
        User expectedUser = new User(login: 'str', password: 1, salt: "str")
        assertEquals(expectedUser, new UserRowMapper().mapRow(rsMock, 1))
    }
}