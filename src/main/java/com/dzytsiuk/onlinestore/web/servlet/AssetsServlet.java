package com.dzytsiuk.onlinestore.web.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class AssetsServlet extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(getClass().getResourceAsStream(requestURI))) {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int count;
            ServletOutputStream outputStream = response.getOutputStream();
            response.setStatus(HttpServletResponse.SC_OK);
            while ((count = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        } catch (IOException e) {
            throw new RuntimeException("Resource is not found " + requestURI, e);
        }
    }
}
