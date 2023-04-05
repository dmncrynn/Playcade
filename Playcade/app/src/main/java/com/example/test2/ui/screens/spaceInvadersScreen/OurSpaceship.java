package com.example.test2.ui.screens.spaceInvadersScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.test2.R;

import java.util.Random;

public class OurSpaceship {

    // Define Object and References within class scope
    Context context;
    Bitmap ourSpaceship;
    int ox, oy;
    boolean isAlive = true;
    int ourVelocity;
    Random random;

    public OurSpaceship(Context context){
        this.context = context;
        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        random = new Random();
        resetOurSpaceship();
    }

    public Bitmap getOurSpaceship(){
        return ourSpaceship;
    }

    int getOurSpaceshipWidth(){
        return ourSpaceship.getWidth();
    }

    int getOurSpaceshipHeight(){
        return ourSpaceship.getHeight();
    }

    private void resetOurSpaceship(){
        ox = random.nextInt(SpaceInvader.screenWidth);
        oy = SpaceInvader.screenHeight - ourSpaceship.getHeight();
        ourVelocity = 10 + random.nextInt(6);

    }
}
