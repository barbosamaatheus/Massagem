package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class CadClienteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento;
    private Button cadastrarC;
    private GerenteRegistros gerenteRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente);

        initViews();
        cadastrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nome = gerarNome();
                    String telefone = mTelefone.getText().toString();
                    String endereco = gerarEndereço();
                    
                    Cliente c = new Cliente(nome, telefone, endereco);
                    gerenteRegistros.writeClient(c);

                    Toast toast = Toast.makeText(getApplicationContext(), "Cadastrado Com Sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Erro! Tente novamente.", Toast.LENGTH_SHORT);
                    toast.show();

                } finally {
                    mNome.setText("");
                    mSobrenome.setText("");
                    mTelefone.setText("");
                    mLogradouro.setText("");
                    mBairro.setText("");
                    mCep.setText("");
                    mNumero.setText("");
                    mComplemento.setText("");
                    
                    gerenteRegistros.readCliente();
                }
                
            }
        });

    }

    private String gerarNome() {
        String nome = mNome.getText().toString() + " " + mSobrenome.getText().toString();
        return nome;
    }

    private String gerarEndereço() {
        String end = mLogradouro.getText().toString() + ", " + mNumero.getText().toString() + ", " + mBairro.getText().toString() +
                ", " + mCep.getText().toString() + ", " + mComplemento.getText().toString();
        return end;
    }


    public void initViews(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Cadastro de Clientes");
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNome = (EditText) findViewById(R.id.nomeC);
        mSobrenome = (EditText) findViewById(R.id.sobrenomeC);
        mTelefone = (EditText) findViewById(R.id.telefoneC);
        mLogradouro = (EditText) findViewById(R.id.endC);
        mBairro = (EditText) findViewById(R.id.bairroC);
        mCep = (EditText) findViewById(R.id.cepC);
        mNumero = (EditText) findViewById(R.id.numC);
        mComplemento = (EditText) findViewById(R.id.complementoC);
        cadastrarC = (Button) findViewById(R.id.cadastrarC);
        gerenteRegistros = (GerenteRegistros) getApplicationContext();

        gerarMascaras();
    }

    private void gerarMascaras() {
        SimpleMaskFormatter cep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtw_cep = new MaskTextWatcher(mCep, cep);
        mCep.addTextChangedListener(mtw_cep);

        SimpleMaskFormatter tel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtw_tel = new MaskTextWatcher(mTelefone, tel);
        mTelefone.addTextChangedListener(mtw_tel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        if (id == android.R.id.home) {
            intent = new Intent(getApplicationContext(), ClientesActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
