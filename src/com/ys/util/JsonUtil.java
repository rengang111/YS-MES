package com.ys.util;

import java.lang.reflect.Type;
import com.google.gson.Gson;

public class JsonUtil
{

    public static <T> String toJson(Object object, Type type) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object, type);
        return jsonStr;
    }

    public static <T> T fromJson(String jsonStr, Type type) {
        Gson gson = new Gson();
        T response = gson.fromJson(jsonStr, type);
        return response;
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object);
        return jsonStr;
    }

 
}
