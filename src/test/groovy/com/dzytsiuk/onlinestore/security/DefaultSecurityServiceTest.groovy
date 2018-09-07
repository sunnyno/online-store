package com.dzytsiuk.onlinestore.security

import com.dzytsiuk.onlinestore.entity.User
import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal
import com.dzytsiuk.onlinestore.entity.security.Session
import com.dzytsiuk.onlinestore.service.UserService
import org.junit.Test

import java.time.LocalDateTime

import static org.junit.Assert.*

class DefaultSecurityServiceTest {
    @Test
    void auth() {
        def expectedSessionUser = new User(id: 0, login: 'zhenya', password: 921188913, salt: 'd6d1b3e9-b581-49a1-b26e-ba7a6c66dbba');
        def userService = { findByLogin -> Optional.of(expectedSessionUser) } as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        def actualSession = securityService.auth('zhenya', 'pass')
        assertTrue(actualSession.isPresent())
        def session = actualSession.get()
        assertNotNull(session.token)
        assertNotNull(session.expireDate)
        assertEquals(expectedSessionUser, session.user)

    }

    @Test
    void authIncorrectUser() {
        def expectedSessionUser = new User(id: 0, login: 'zhenya', password: 921188913, salt: 'd6d1b3e9-b581-49a1-b26e-ba7a6c66dbba');
        def userService = { findByLogin -> Optional.of(expectedSessionUser) } as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        def actualSession = securityService.auth('zhenya', 'wrongPass')
        assertFalse(actualSession.isPresent())

    }

    @Test
    void logout() {
        def userService = {} as UserService
        def user = new User(id: 0, login: 'zhenya', password: 921188913, salt: 'd6d1b3e9-b581-49a1-b26e-ba7a6c66dbba');
        def authPrincipal = { getUser -> user } as AuthPrincipal
        SecurityService securityService = new DefaultSecurityService(userService)
        def sessions = securityService.getSessions()
        def session = new Session(null, user, null, null)
        sessions.add(session)
        assertTrue(sessions.contains(session))
        securityService.logout(authPrincipal)
        assertFalse(sessions.contains(session))
    }

    @Test
    void getSessionByToken() {
        def token = 'token'
        def userService = {} as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        Session session = new Session(token, null, LocalDateTime.now().plusSeconds(1000), null)
        securityService.getSessions().add(session)
        def actualSession = securityService.getSessionByToken(token)
        assertTrue(actualSession.isPresent())
        assertEquals(session, actualSession.get())
    }

    @Test
    void getExpiredSessionByToken() {
        def token = 'token'
        def userService = {} as UserService
        SecurityService securityService = new DefaultSecurityService(userService)
        Session session = new Session(token, null, LocalDateTime.now().minusSeconds(1000), null)
        securityService.getSessions().add(session)
        def actualSession = securityService.getSessionByToken(token)
        assertFalse(actualSession.isPresent())
    }

}
