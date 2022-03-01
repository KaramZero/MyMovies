package com.example.mymovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentA extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public static List<Movie> moviesList;
    private Handler handler;
    private static final String jsonUrl = "https://api.androidhive.info/json/movies.json";

    Context context;
    static Communicator com;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com = (Communicator) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context= view.getContext();
        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        moviesList = new ArrayList<>();

        getData();



        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(new MyRecyclerAdapter(context, moviesList));

            }
        };

    }


    public void getData() {




        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Movie>> call = apiInterface.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                moviesList= response.body();
                handler.sendEmptyMessage(0);

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });





      /*  new Thread() {
            @Override
            public void run() {
                String response = null;
                try {
                    URL url = new URL(jsonUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);

                } catch (MalformedURLException e) {
                    Log.e("TAG", "MalformedURLException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("TAG", "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e("TAG", "Exception: " + e.getMessage());
                }


                JSONObject jsonObj = null;
                try {
                    JSONArray movies = new JSONArray(response);
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject c = movies.getJSONObject(i);
                        String title = c.getString("title");
                        String image = c.getString("image");
                        String rating = c.getString("rating");
                        String releaseYear = c.getString("releaseYear");

                        Movie movie = new Movie();

                        JSONArray arrJson = c.getJSONArray("genre");
                        List<String> genre = new ArrayList<String>();

                        for (int j = 0; j < arrJson.length(); j++) {
                            genre.add(arrJson.getString(j));
                        }

                        movie.setTitle(title);
                        movie.setImage(image);
                        movie.setRating(Float.parseFloat(rating));
                        movie.setReleaseYear(Integer.parseInt(releaseYear));
                        movie.setGenre(genre);

                        moviesList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("TAG", "Internet : List Downloaded ");
                handler.sendEmptyMessage(0);
            }
        }.start();*/
    }
/*
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }*/
/*
    public List<Movie> downloadImages(List<Movie> movies) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        URL url;
        HttpsURLConnection httpsURLConnection = null;
        for (int i = 0; i < movies.size(); i++) {
            try {
                url = new URL(movies.get(i).getImage());
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();

                if (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpsURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    movies.get(i).setBitmap(bitmap);
                    inputStream.close();
                    httpsURLConnection.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return movies;
    }

    public class MyAsyncTask extends AsyncTask<List<Movie>, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(List<Movie>... lists) {
            List<Movie> movies = downloadImages(lists[0]);

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            Message msg = new Message();
            msg.obj = movies;
            handler.sendMessage(msg);
        }
    }*/
}