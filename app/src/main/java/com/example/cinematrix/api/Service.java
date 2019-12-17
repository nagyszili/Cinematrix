package com.example.cinematrix.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key, @Query("page") int pageIndex);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int pageIndex);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideo(@Path("movie_id") int id, @Query("api_key") String api_key);

}
