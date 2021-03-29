package com.example.spacexcrew.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpaceXClient {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/v4/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static SpaceXService api = retrofit.create(SpaceXService.class);
}
