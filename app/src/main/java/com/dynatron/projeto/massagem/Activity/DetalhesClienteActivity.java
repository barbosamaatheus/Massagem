package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.R;

public class DetalhesClienteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento;
    private ImageButton mDelete, mSave;
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);
        initViews();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    private void initViews() {
        myApplication = (MyApplication) getApplicationContext();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tb_dc);
        myToolbar.setTitle("Cadastro");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNome = (EditText) findViewById(R.id.dNome);
        mSobrenome = (EditText) findViewById(R.id.dSobrenome);
        mTelefone = (EditText) findViewById(R.id.dTelefone);
        mLogradouro = (EditText) findViewById(R.id.dLogradouro);
        mBairro = (EditText) findViewById(R.id.dBairro);
        mCep = (EditText) findViewById(R.id.dCep);
        mNumero = (EditText) findViewById(R.id.dNum);
        mComplemento = (EditText) findViewById(R.id.dComplemento);
        mComplemento = (EditText) findViewById(R.id.dComplemento);
        mDelete = (ImageButton) findViewById(R.id.dDelete);
        mSave = (ImageButton) findViewById(R.id.dSave);

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
                Uri uri = Uri.parse("tel:" + "83988121204");
                intent = new Intent(Intent.ACTION_DIAL, uri);
                break;
            case R.id.action_map:
                String urii = "https://goo.gl/maps";
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urii));
                break;
            case R.id.action_edit:
                enableCampos(true);

        }
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        Toast toast = Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void delete() {
        Toast toast = Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void enableCampos(boolean b) {

        /*mNome.setEnabled(b);
        mSobrenome.setEnabled(b);
        mTelefone.setEnabled(b);
        mLogradouro.setEnabled(b);
        mBairro.setEnabled(b);
        mCep.setEnabled(b);
        mNumero.setEnabled(b);
        mComplemento.setEnabled(b);*/
        //mDelete.setVisibility(View.VISIBLE);
       // mSave.setVisibility(View.VISIBLE);


    }
}
