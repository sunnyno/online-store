package com.dzytsiuk.onlinestore.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AssetsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        try {
            Path path = Paths.get(getClass().getResource(requestURI).toURI());
            String contentType = Files.probeContentType(path);
            response.setContentType(contentType);
            Files.copy(path, response.getOutputStream());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Assets URI error", e);
        }
    }
}
