package com.example.mymovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FragmentB extends Fragment {

    int index;

    ImageView imageView;
    TextView titleTXTView;
    TextView yearTXTView;
    RatingBar ratingBar;
    TextView genreTXTView;
    Handler handler;
    Bitmap bitmap;


    public FragmentB(){
    }

    FragmentB(int index){
        this.index=index;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState != null)
            index = savedInstanceState.getInt("index");

        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        titleTXTView = view.findViewById(R.id.titleTx);
        yearTXTView = view.findViewById(R.id.releaseYearTx);
        genreTXTView = view.findViewById(R.id.genreTx);
        ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(10);

        change(index);


    }

    void change(int in){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Movie movie = FragmentA.moviesList.get(in);

        List<String> l = movie.getGenre();
        StringBuilder builder = new StringBuilder(l.get(0));
        for (int i=1;i<l.size();i++)
            builder.append(" , "+l.get(i));

        titleTXTView.setText(movie.getTitle());
        yearTXTView.setText(movie.getReleaseYear()+"");
        genreTXTView.setText(builder.toString());
        ratingBar.setRating(movie.getRating());

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {

                imageView.setImageBitmap(bitmap);


            }
        };

        new Thread(){
            @Override
            public void run() {
                bitmap = download(movie.getImage());
                handler.sendEmptyMessage(0);
            }
        }.start();


    }

    Bitmap download(String url) {

        Bitmap res = null;
        URL urlobj;
        HttpsURLConnection connection;
        InputStream stream;

        try {
            urlobj = new URL(url);
            connection = (HttpsURLConnection) urlobj.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                res = BitmapFactory.decodeStream(stream);
                stream.close();
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (res != null)
            Log.i("TAG", "doInBackground:  downloaded");
        return res;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",index);
    }
}




interface Communicator{
    void respond(int i);
}