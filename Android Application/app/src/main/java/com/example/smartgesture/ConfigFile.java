package com.example.smartgesture;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ConfigFile {
    private static String BASE_URL = "http://10.70.166.97:5000/";
    static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ConfigFile.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

