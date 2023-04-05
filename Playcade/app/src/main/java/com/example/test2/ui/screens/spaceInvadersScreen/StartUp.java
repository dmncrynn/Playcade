package com.example.test2.ui.screens.spaceInvadersScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test2.R;

public class StartUp extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
    }

    // Define startGame method
    public void startGame(View v){
        startActivity(new Intent(this, SpaceInvaderActivity.class));
    }
}
