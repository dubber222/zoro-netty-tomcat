package com.zoro.catalina.servlets;

import com.zoro.catalina.http.ZoroRequest;
import com.zoro.catalina.http.ZoroResponse;
import com.zoro.catalina.http.ZoroServlet;

import java.io.UnsupportedEncodingException;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public class DemoServlet extends ZoroServlet {
    @Override
    public void doGet(ZoroRequest request, ZoroResponse response) {
        try {
            response.write(request.getParameter("name"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(ZoroRequest request, ZoroResponse response) {
        doGet(request,response);
    }
}
