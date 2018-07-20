package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.R;

public class DetalhesClienteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private TextView mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);
        initViews();
    }

    private void initViews() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tb_dc);
        myToolbar.setTitle("Cadastro");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNome = (TextView) findViewById(R.id.dNome);
        mSobrenome = (TextView) findViewById(R.id.dSobrenome);
        mTelefone = (TextView) findViewById(R.id.dTelefone);
        mLogradouro = (TextView) findViewById(R.id.dLogradouro);
        mBairro = (TextView) findViewById(R.id.dBairro);
        mCep = (TextView) findViewById(R.id.dCep);
        mNumero = (TextView) findViewById(R.id.dNum);
        mComplemento = (TextView) findViewById(R.id.dComplemento);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalhe_clientes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                intent = new Intent(getApplicationContext(), ClientesActivity.class);
                break;
            case R.id.action_tel:
                Uri uri = Uri.parse("tel:"+"83988121204");
                intent = new Intent(Intent.ACTION_DIAL,uri);
                break;
            case R.id.action_map:
                String urii = "https://goo.gl/maps";
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urii));
                break;

        }
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
