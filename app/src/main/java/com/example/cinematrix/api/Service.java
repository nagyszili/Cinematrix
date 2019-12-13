package com.example.cinematrix.api;

import com.example.cinematrix.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key, @Query("page") int pageIndex);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String api_key,@Query("page") int pageIndex);

}
