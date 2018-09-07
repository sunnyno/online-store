package com.dzytsiuk.onlinestore.web.controller

import com.dzytsiuk.onlinestore.entity.security.Session
import com.dzytsiuk.onlinestore.security.SecurityService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@RunWith(MockitoJUnitRunner.class)
class LoginControllerTest {
    @InjectMocks
    LoginController loginController

    @Mock
    SecurityService securityService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build()
    }

    @Test
    void showLoginPage() {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
    }

    @Test
    void doLogin() {
        Session session = new Session('token', null, LocalDateTime.now().minusSeconds(1000), null)
        Mockito.when(securityService.auth(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(session))
        Mockito.when(securityService.getSessionTimeToLive((Session) Mockito.notNull())).thenReturn(1000L)
        RequestBuilder requestBuilder = post("/login")
                .param("login", 'login')
                .param("password", 'password')
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"))

    }
}
