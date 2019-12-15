package com.example.cinematrix.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematrix.MovieResult;
import com.example.cinematrix.MoviesResponse;
import com.example.cinematrix.R;
import com.example.cinematrix.TopMoviesAdapter;
import com.example.cinematrix.api.Service;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static int PAGE = 1;
    public static String API_KEY = "99fd430c3be6ec05d080de47353ce38e";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

    private Context context;
    private TopMoviesAdapter adapter;
    private List<MovieResult> movies = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeFragmentRecyclerView);
        layoutManager = new LinearLayoutManager(context);


        if(movies.isEmpty())
        {
            initMovieList();
        } else {
//            loadMoreData(++PAGE);
        }



//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            /**
//             * z
//             * @param recyclerView
//             * @param dx
//             * @param dy
//             */
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
//                    if (PAGE <= 5) {
//                        loadMoreData(++PAGE);
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    } else {
//                        return;
//                    }
//                }
//            }
//        });

        return view;
    }

    private void initMovieList() {


        Call<MoviesResponse> call = apiInterface().getPopularMovies(API_KEY, PAGE);


        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse responseBody = response.body();
                try {
                    List<MovieResult> movieList = responseBody.getMovieResults();
//                    Toast.makeText(getContext(), movieList.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                    movies.addAll(movieList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new TopMoviesAdapter(context, movieList));
                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (Exception e) {
//                    Toast.makeText(getContext(), "Something went wrong:" + e.toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("HomeFragmentResponse", "Failed api call!");

            }
        });



    }

    private void loadMoreData(int pageNumber) {

        Call<MoviesResponse> call = apiInterface().getPopularMovies(API_KEY, pageNumber);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResult> movieList = response.body().getMovieResults();
                movies.addAll(movieList);

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private Service apiInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        return apiService;
    }
}
