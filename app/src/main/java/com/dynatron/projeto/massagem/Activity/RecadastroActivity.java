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
import android.widget.ImageButton;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class RecadastroActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText mNome, mSobrenome, mTelefone, mLogradouro, mBairro, mCep, mNumero, mComplemento;
    private ImageButton mSave, mDelete;
    private MyApplication myApplication;
    private String oldNome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recadastro);
        initViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
        if (!txt.isEmpty()) {
            editarCampos(txt);
        }

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

    private void delete() {
        try {alertDialog("Tem certeza que deseja DELETAR este cliente?", "APAGAR", "VOLTAR");

        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Erro ao Deletar! \n Verifique se preencheu todos os campos e tente novamente", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void save() {
        try {
            if (!validarCampos(mNome.getText().toString(), mSobrenome.getText().toString())) {
                String nome = gerarNome();
                String telefone = mTelefone.getText().toString();
                String endereco = gerarEndereço();
                Cliente c = new Cliente(nome, telefone, endereco);
                myApplication.editClient(c, oldNome);
                Toast toast = Toast.makeText(getApplicationContext(), "Edição Realizada Com Sucesso!", Toast.LENGTH_SHORT);
                toast.show();
                callClientesActivity();
            }

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Erro ao Editar! \n Verifique se preencheu todos os campos e tente novamente", Toast.LENGTH_SHORT);
            toast.show();

        } finally {

            myApplication.readClient();
        }
    }

    private void alertDialog(String msg, String p, String n) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RecadastroActivity.this);
        builder.setTitle("APAGAR CLIENTE");
        builder.setMessage(msg)
                .setPositiveButton(p, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myApplication.deleteClient(gerarNome());
                        Toast toast = Toast.makeText(getApplicationContext(), "Deletado Com Sucesso!", Toast.LENGTH_SHORT);
                        toast.show();
                        myApplication.readClient();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    }
                })

                .setNegativeButton(n, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void gerarOldNome() {
        String nome = mNome.getText().toString() + " " + mSobrenome.getText().toString();
        this.oldNome = nome;
    }

    private void editarCampos(String txt) {
        String[] dados = txt.split("//");
        mNome.setText(dados[0]);
        mSobrenome.setText(dados[1]);
        mTelefone.setText(dados[2]);
        mLogradouro.setText(dados[3]);
        mNumero.setText(dados[4]);
        mBairro.setText(dados[5]);
        mCep.setText(dados[6]);
        mComplemento.setText(dados[7]);
        gerarOldNome();
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

    private String gerarNome() {
        String nome = mNome.getText().toString() + " " + mSobrenome.getText().toString();
        return nome;
    }

    private String gerarEndereço() {
        String end = mLogradouro.getText().toString() + "//" + mNumero.getText().toString() + "//" +
                mBairro.getText().toString() + "//" + mCep.getText().toString() + "//" + mComplemento.getText().toString();
        return end;
    }

    public void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Editar Cliente");
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNome = (EditText) findViewById(R.id.nomeE);
        mSobrenome = (EditText) findViewById(R.id.sobrenomeE);
        mTelefone = (EditText) findViewById(R.id.telefoneE);
        mLogradouro = (EditText) findViewById(R.id.endE);
        mBairro = (EditText) findViewById(R.id.bairroE);
        mCep = (EditText) findViewById(R.id.cepE);
        mNumero = (EditText) findViewById(R.id.numE);
        mComplemento = (EditText) findViewById(R.id.complementoE);
        mSave = (ImageButton) findViewById(R.id.eSave);
        mDelete = (ImageButton) findViewById(R.id.eDelete);
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

        int id = item.getItemId();
        if (id == android.R.id.home) {
            callClientesActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void callClientesActivity(){
        Intent intent = new Intent(getApplicationContext(), ClientesActivity.class);
        startActivity(intent);
        finish();

    }
}
