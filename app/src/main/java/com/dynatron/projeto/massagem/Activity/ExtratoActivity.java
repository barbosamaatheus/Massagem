package com.dynatron.projeto.massagem.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.R;

public class ExtratoActivity extends AppCompatActivity {
    private TextView mMes, mQuantR, mReceita, mQuantD, mDespesa, mQuant;
    private TextView mTotal;
    private MyApplication myApplication;
    private Toolbar myToolbar;
    private String txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        txt = bundle.getString("txt");


        initViews();

        myToolbar.setTitle("Extrato");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setCampos(txt);
    }

    private void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myApplication = (MyApplication) getApplicationContext();
        mMes = (TextView) findViewById(R.id.dataExtrato);
        mQuantR = (TextView) findViewById(R.id.quantRExtrato);
        mReceita = (TextView) findViewById(R.id.receitaExtrato);
        mQuantD = (TextView) findViewById(R.id.quantDExtrato);
        mDespesa = (TextView) findViewById(R.id.despesaExtrato);
        mQuant = (TextView) findViewById(R.id.quantExtrato);
        this.mTotal = (TextView) findViewById(R.id.totalE);
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
        this.mTotal.setText(myApplication.getValorTMes(txt));

    }

}