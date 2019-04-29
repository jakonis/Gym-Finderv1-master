package com.example.gymfinder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);

        this.deleteDatabase("databasename.db");

        //splash screen will be shown for 5 seconds
        int SPLASH_DISPLAY_LENGTH = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent;
                mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

