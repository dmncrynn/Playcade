package com.example.test2.ui.screens.spaceInvadersScreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SpaceInvaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: SpaceInvader class to be later defined

        setContentView(
                new SpaceInvader(this)
        );
    }
}