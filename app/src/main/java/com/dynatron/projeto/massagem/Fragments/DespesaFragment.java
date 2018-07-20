package com.dynatron.projeto.massagem.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dynatron.projeto.massagem.Activity.MainActivity;
import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Extras.MoneyTextWatcher;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;


public class DespesaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText mDescricao, textData, mValor;
    private ImageButton mData;
    private Button cadastrarD;
    private MyApplication myApplication;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();

    public DespesaFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_despesa, container, false);

        initViews(view);

        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        cadastrarD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String desc = mDescricao.getText().toString();
                    String data = textData.getText().toString();
                    String valor = mValor.getText().toString();

                    if(!validarCampos(desc,data,valor)){
                        Registros r = new Registros(desc, data, valor);
                        r.setTipo("D");
                        myApplication.writeRegistros(r);
                        alertDialog("Cadastrado Com Sucesso!", "Novo Cadastro", "Voltar p/ Registros");
                    }
                } catch (Exception e) {
                    alertDialog("Erro ao Cadastrar! \n Verifique se preencheu todos os campos e tente novamente",
                            "Tentar Novamente", "Voltar p/ Registros");
                } finally {
                    myApplication.readRegistros();
                }

            }
        });
        return view;
    }

    public void updateData() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        textData.setText(editarData(date));
    }

    private String editarData(String date) {
        String[] array = date.split("/");
        if (array[0].length() == 1) {
            array[0] = "0" + array[0].toString();
        }
        if ((array[1].length() == 1)) {
            array[1] = "0" + array[1].toString();
        }
        return array[0] + "/" + array[1] + "/" + array[2];

    }

    private void gerarMascaras() {
        mValor.addTextChangedListener(new MoneyTextWatcher(mValor));

    }

    private void initViews(View view) {
        mDescricao = (EditText) view.findViewById(R.id.descricaoD);
        textData = (EditText) view.findViewById(R.id.textDataD);
        mData = (ImageButton) view.findViewById(R.id.dataD);
        mValor = (EditText) view.findViewById(R.id.valorD);
        cadastrarD = (Button) view.findViewById(R.id.cadastrarD);
        myApplication = (MyApplication) getActivity().getApplicationContext();
    }

    public boolean validarCampos(String desc,String data, String valor ) {

        View focus = null;
        boolean exibir = false;
        if (desc.equals("Nome do Cliente")) {
            mDescricao.setFocusable(true);
            mDescricao.setFocusableInTouchMode(true);
            exibir = true;
        }

        if (data.isEmpty()) {
            textData.setError("Campo vazio");
            focus = textData;
            exibir = true;
        }
        if (valor.isEmpty()) {
            mValor.setError("Campo vazio ");
            focus = mValor;
            exibir = true;
        }
        if (exibir) {
            focus.requestFocus();
        }
        return exibir;

    }

    private void alertDialog(String msg, String p, String n){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cadastro");
        builder.setMessage(msg)
                .setPositiveButton(p, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDescricao.setText("");
                        textData.setText("");
                        mValor.setText("");
                    }
                })
                .setNegativeButton(n, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
