package com.example.pooria.boomranq.Utils;

import com.example.pooria.boomranq.Retrofit.MyBoomranQAPI;
import com.example.pooria.boomranq.Retrofit.RetrofitClient;

public class Common {

    public static final String BASE_URL = "http://192.168.1.10/MyBoomranQ/";


    public static MyBoomranQAPI getAPI(){

        return RetrofitClient.getClient(BASE_URL).create(MyBoomranQAPI.class);
    }
}
