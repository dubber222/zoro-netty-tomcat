package com.zoro.catalina.http;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public abstract class ZoroServlet {

    public abstract void doGet(ZoroRequest request,ZoroResponse response);
    public abstract void doPost(ZoroRequest request,ZoroResponse response);
}
