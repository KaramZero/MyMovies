package com.example.mymovies;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
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


public class MainActivity extends AppCompatActivity implements Communicator{

    FragmentB fragmentB;
    FragmentManager fm;

    int index;
    Bundle saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saved = savedInstanceState;
        if (savedInstanceState != null)
            index = savedInstanceState.getInt("index");

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){

        }else {

            fm = getSupportFragmentManager();

            if (savedInstanceState != null){
                fragmentB = (FragmentB) fm.findFragmentByTag("FragB");
                if(fragmentB==null){
                    fragmentB = new FragmentB(index);
                    fm.beginTransaction().replace(R.id.MyFragmentB,fragmentB,"FragB").commit();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",index);

    }

    @Override
    public void respond(int i) {

        index =i;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this,MainActivity2.class);
            intent.putExtra("index",i);
            startActivity(intent);
        }else {

            runFragB(saved,i);
        }

    }
    void runFragB(Bundle save , int index){
        if (save == null){
            fragmentB = new FragmentB(index);
            fm.beginTransaction().replace(R.id.MyFragmentB,fragmentB,"FragB").commit();
        }
        else {
            fragmentB = (FragmentB) fm.findFragmentByTag("FragB");
            fragmentB.change(index);
        }


    }

}