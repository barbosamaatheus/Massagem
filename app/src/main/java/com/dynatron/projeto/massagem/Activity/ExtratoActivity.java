package com.dynatron.projeto.massagem.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Manifest;
import com.dynatron.projeto.massagem.R;

import java.io.OutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ExtratoActivity extends AppCompatActivity {
    private TextView mMes, mQuantR, mReceita, mQuantD, mDespesa, mQuant, mTotal;
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        initViews();

    }

    private void initViews() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Extrato");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        myApplication = (MyApplication) getApplicationContext();
        mMes = (TextView) findViewById(R.id.dataExtrato);
        mQuantR = (TextView) findViewById(R.id.quantRExtrato);
        mReceita = (TextView) findViewById(R.id.receitaExtrato);
        mQuantD = (TextView) findViewById(R.id.quantDExtrato);
        mDespesa = (TextView) findViewById(R.id.despesaExtrato);
        mQuant = (TextView) findViewById(R.id.quantExtrato);
        mTotal = (TextView) findViewById(R.id.totalExtrato);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
        setCampos(txt);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        if (id == android.R.id.home) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_export) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCampos(String txt) {
        mMes.setText(txt);
        mQuant.setText(myApplication.getQuant(txt));
        mQuantR.setText(myApplication.getQuantReceita(txt));
        mReceita.setText(myApplication.getReceitaMes(txt));
        mQuantD.setText(myApplication.getQuantDespesa(txt));
        mDespesa.setText(myApplication.getDespesaMes(txt));
        mTotal.setText(myApplication.getValorTotalMes(txt));

    }

}