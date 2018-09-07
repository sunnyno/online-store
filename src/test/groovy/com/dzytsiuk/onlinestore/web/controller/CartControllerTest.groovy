package com.dzytsiuk.onlinestore.web.controller

import com.dzytsiuk.onlinestore.entity.CartItem
import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal
import com.dzytsiuk.onlinestore.entity.security.Session
import com.dzytsiuk.onlinestore.service.ProductService
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

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@RunWith(MockitoJUnitRunner.class)
class CartControllerTest {

    @InjectMocks
    CartController cartController

    @Mock
    ProductService productService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartController).build()
    }

    @Test
    void showCart() {
        Session session = new Session('token', null, LocalDateTime.now().plusSeconds(1000), new ArrayList<CartItem>())
        def authPrincipal = [getSession: { _ -> session }, getName: { _ -> 'principal' }] as AuthPrincipal

        RequestBuilder requestBuilder = get("/cart")
                .principal(authPrincipal)

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("cart.html"))
    }

    @Test
    void addToCart() {
        Session session = new Session('token', null, LocalDateTime.now().plusSeconds(1000), new ArrayList<CartItem>())
        def authPrincipal = [getSession: { _ -> session }, getName: { _ -> 'principal' }] as AuthPrincipal
        RequestBuilder requestBuilder = post("/cart/10")
                .principal(authPrincipal)
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("products.html"))
    }

    @Test
    void deleteFromCart() {
        Session session = new Session('token', null, LocalDateTime.now().plusSeconds(1000), new ArrayList<CartItem>())
        def authPrincipal = [getSession: { _ -> session }, getName: { _ -> 'principal' }] as AuthPrincipal
        RequestBuilder requestBuilder = delete("/cart/10")
                .principal(authPrincipal)
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())

    }
}
