package com.example.mymovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("json/movies.json")
    Call<List<Movie>> getMovies();
}
