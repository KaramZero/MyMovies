package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    FragmentB fragmentB;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {


            fm = getSupportFragmentManager();

            if (savedInstanceState == null) {

                fragmentB = new FragmentB(intent.getIntExtra("index", 0));
                fm.beginTransaction().replace(R.id.MyBFragment, fragmentB, "FragB").commit();
            } else {
                fragmentB = (FragmentB) fm.findFragmentByTag("FragB");
            }
        } else {
            finish();
        }

    }
}