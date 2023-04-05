package com.example.test2.ui.screens.spaceInvadersScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.test2.R;

public class Shot {
    Bitmap shot;
    Context context;
    int shx, shy;

    public Shot(Context context, int shx, int shy){
        this.context = context;
        shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        this.shx = shx;
        this.shy = shy;
    }

    public Bitmap getShot(){
        return shot;
    }
}
