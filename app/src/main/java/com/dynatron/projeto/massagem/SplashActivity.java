package com.dynatron.projeto.massagem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity implements Runnable {
    private static final long delay = 3500;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        progressBar = (ProgressBar) findViewById(R.id.progressBar_splash);

        Handler h = new Handler();
        h.postDelayed(this, delay);
    }

    @Override
    public void run() {
        callNextActivity();
    }

    private void callNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
