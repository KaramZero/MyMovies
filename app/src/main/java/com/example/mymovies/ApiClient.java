package com.example.mymovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static  String URL ="https://api.androidhive.info/";
    private static Retrofit retrofit;

    public static  Retrofit getClient(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
