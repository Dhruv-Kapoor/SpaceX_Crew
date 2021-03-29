package com.example.spacexcrew.network;

import com.example.spacexcrew.dataClasses.Crew;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpaceXService {

    @GET("crew")
    Call<List<Crew>> getCrew();

}
