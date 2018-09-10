package com.dzytsiuk.onlinestore.web.controller

import com.dzytsiuk.onlinestore.entity.Product
import com.dzytsiuk.onlinestore.security.SecurityService
import com.dzytsiuk.onlinestore.service.ProductService
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {
    @InjectMocks
    ProductController productController

    @Mock
    ProductService productService

    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build()
    }

    @Test
    void showProducts() {
        Mockito.when(productService.findAll()).thenReturn(new ArrayList<Product>())
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products.html"))
    }

    @Test
    void showAddProductForm() {
        mockMvc.perform(get("/product/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addProduct.html"))
    }

    @Test
    void addProduct() {
        RequestBuilder requestBuilder = post("/product/add")
                .param("name", 'name')
                .param("price", 1.0 as String)
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
    }
}
