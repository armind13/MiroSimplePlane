package com.example.demo.integration;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlHelper {

    public static String getCreateUrl(int port) {
        return createUrl(port, "create");
    }

    public static String getUpdateUrl(int port) {
        return createUrl(port, "update");
    }

    public static  String getGetUrl(int port) {
        return createUrl(port, "get");
    }

    public static  String getGetUrl(int port, Long id) {
        var url = getGetUrl(port);
        if (id == null)
            return url;

        return url + "?id=" + id;
    }

    public static  String getGetAllUrl(int port) {
        return createUrl(port, "get-all");
    }

    public static String getDeleteUrl(int port, Long id) {
        var url = createUrl(port, "delete");
        if (id == null)
            return url;
        return url + "?id=" + id;
    }

    public static String getDeleteUrl(int port) {
        return createUrl(port, "delete");
    }

    private static String createUrl(int port, String method) {
        try {
            var url = new URL("http", "localhost", port, "widget/" + method);
            return url.toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
