package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.R;

public class SplashActivity extends AppCompatActivity implements Runnable {
    private static final long delay = 4000;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toast toast;
        try{
            GerenteRegistros gerenteRegistros = (GerenteRegistros) getApplicationContext();
            gerenteRegistros.readFireStore();
            gerenteRegistros.readCliente();
            toast = Toast.makeText(getApplicationContext(), "Carregando seus dados...", Toast.LENGTH_LONG);
            toast.show();
        }catch (Exception e){
            toast = Toast.makeText(getApplicationContext(), "Ocorreu um erro, reinicie o aplicativo", Toast.LENGTH_LONG);
            toast.show();
        }finally {
            Handler h = new Handler();
            h.postDelayed(this, delay);
        }
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
