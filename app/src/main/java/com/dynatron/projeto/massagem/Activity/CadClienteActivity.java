package com.dynatron.projeto.massagem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class CadClienteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento;
    private Button cadastrarC;
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente);
        initViews();


        cadastrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!validarCampos(mNome.getText().toString(), mSobrenome.getText().toString())){

                        String nome = gerarNome();
                        String telefone = mTelefone.getText().toString();
                        String endereco = gerarEndereço();
                        Cliente c = new Cliente(nome, telefone, endereco);
                        myApplication.writeClient(c);
                        alertDialog("Cadastrado Com Sucesso!", "Novo Cadastro", "Voltar p/ Clientes");

                    }

                } catch (Exception e) {
                    alertDialog("Erro ao Cadastrar! \n Verifique se preencheu todos os campos e tente novamente",
                            "Tentar Novamente", "Voltar p/ Registros");

                } finally {
                    myApplication.readClient();
                }

            }
        });

    }

    public boolean validarCampos(String nome, String sobrenome) {
        View focus = null;
        boolean exibir = false;

        if (nome.isEmpty()) {
            mNome.setError("Campo vazio");
            focus = mNome;
            exibir = true;
        }
        if (sobrenome.isEmpty()) {
            mSobrenome.setError("Campo vazio");
            focus = mSobrenome;
            exibir = true;
        }
        if (exibir) {
            focus.requestFocus();
        }
        return exibir;

    }

    private void alertDialog(String msg, String p, String n) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CadClienteActivity.this);
        builder.setTitle("Cadastro");
        builder.setMessage(msg)
                .setPositiveButton(p, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mNome.setText("");
                        mSobrenome.setText("");
                        mTelefone.setText("");
                        mLogradouro.setText("");
                        mBairro.setText("");
                        mCep.setText("");
                        mNumero.setText("");
                        mComplemento.setText("");
                    }
                })
                .setNeutralButton("Cadastrar Massagens", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(n, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), ClientesActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String gerarNome() {
        String nome = mNome.getText().toString() + " " + mSobrenome.getText().toString();
        return nome;
    }

    private String gerarEndereço() {
        String end = mLogradouro.getText().toString() + " // " + mNumero.getText().toString() + " // " +
                mBairro.getText().toString() + " // " + mCep.getText().toString() + " // " + mComplemento.getText().toString();
        return end;
    }

    public void initViews() {
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
        myApplication = (MyApplication) getApplicationContext();

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
