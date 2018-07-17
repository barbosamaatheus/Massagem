package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.R;

public class SplashActivity extends AppCompatActivity implements Runnable {
    private static final long delay = 4000;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GerenteRegistros gerenteRegistros = (GerenteRegistros) getApplicationContext();
        gerenteRegistros.readFireStore();
        gerenteRegistros.readCliente();

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
