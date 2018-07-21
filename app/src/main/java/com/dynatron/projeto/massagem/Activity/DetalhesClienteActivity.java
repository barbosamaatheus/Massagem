package com.dynatron.projeto.massagem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.R;

public class DetalhesClienteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private TextView mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento, mTotalMassagens;
    private MyApplication myApplication;
    private Cliente c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");

        initViews();
        initClient(txt);

    }

    private void initClient(String nome) {
        for (Cliente cliente : myApplication.getClientes()) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                String[] nomeComp = cliente.getNome().split(" ");
                String[] splitEnde = cliente.getEndereço().split("//");
                mNome.setText(nomeComp[0]);
                mSobrenome.setText(nomeComp[1]);
                mTelefone.setText(cliente.getTelefone());
                mLogradouro.setText(splitEnde[0]);
                mNumero.setText(splitEnde[1]);
                mBairro.setText(splitEnde[2]);
                mCep.setText(splitEnde[3]);
                mComplemento.setText(splitEnde[4]);
                mTotalMassagens.setText(cliente.getNumTotal());
            }

        }

    }

    private void initViews() {
        myApplication = (MyApplication) getApplicationContext();
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
        mTotalMassagens = (TextView) findViewById(R.id.dTotalMassagens);

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
                Uri uri = Uri.parse("tel:" + mTelefone.getText().toString());
                intent = new Intent(Intent.ACTION_DIAL, uri);
                break;
            case R.id.action_map:
                String urii = "https://goo.gl/maps";
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urii));
                break;
            case R.id.action_edit:
                intent = new Intent(getApplicationContext(), RecadastroActivity.class);
                String txt = mNome.getText().toString() + "//" + mSobrenome.getText().toString() + "//"
                        + mTelefone.getText().toString() + "//" + mLogradouro.getText().toString() + "//" + mNumero.getText().toString() +
                        "//" + mBairro.getText().toString() + "//" + mCep.getText().toString() +
                        "//" + mComplemento.getText().toString()+"//"+"true";
                Bundle bund = new Bundle();
                bund.putString("txt", txt);
                intent.putExtras(bund);

        }
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }


}
