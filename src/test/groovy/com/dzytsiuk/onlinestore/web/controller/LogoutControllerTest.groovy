package com.dzytsiuk.onlinestore.web.controller

import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal
import com.dzytsiuk.onlinestore.security.SecurityService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@RunWith(MockitoJUnitRunner.class)
class LogoutControllerTest {
    @InjectMocks
    LogoutController logoutController

    @Mock
    SecurityService securityService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build()
    }


    @Test
    void doLogout() {
        def authPrincipal = [getSession: { _ -> session }, getName: { _ -> 'principal' }] as AuthPrincipal
        RequestBuilder requestBuilder = get("/logout")
                .principal(authPrincipal)
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
    }
}
