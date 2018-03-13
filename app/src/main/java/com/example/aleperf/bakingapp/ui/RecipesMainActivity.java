package com.example.aleperf.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aleperf.bakingapp.R;

/**
 * Launcher for the app, coordinates fragments.
 */
public class RecipesMainActivity extends AppCompatActivity {

    private static String TAG = RecipesMainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipes);
        //add fragment transaction
    }



}