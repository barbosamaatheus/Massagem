package com.dynatron.projeto.massagem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Extras.MoneyTextWatcher;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DespesaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private ProgressBar progressBar;
    private EditText mDescricao, textData, mValor;
    private Button mData;
    private Button cadastrarD;
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
            GerenteRegistros gr = (GerenteRegistros) getActivity().getApplicationContext();
            @Override
            public void onClick(View v) {
                try{
                    progressBar.setVisibility(View.VISIBLE);
                    String desc = mDescricao.getText().toString();
                    String data = textData.getText().toString();
                    String valor = mValor.getText().toString();
                    //.substring(2);
                    Registros r = new Registros(desc, data, valor);
                    r.setTipo("D");

                    gr.writeFireStore(r);

                    Toast toast = Toast.makeText(getActivity(), "Cadastrado Com Sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception e){
                    Toast toast = Toast.makeText(getActivity(), "Erro! Tente novamente.", Toast.LENGTH_SHORT);
                    toast.show();
                }finally {
                    mDescricao.setText("");
                    textData.setText("");
                    mValor.setText("");
                    progressBar.setVisibility(View.GONE);
                    gr.readFireStore();

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
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        textData.setText(date);
    }

    private void gerarMascaras() {
        //Locale mLocale = new Locale("pt", "BR");
        //mValor.addTextChangedListener(new MoneyTextWatcher(mValor));
    }

    private void initViews(View view){

        mDescricao = (EditText) view.findViewById(R.id.descricaoD);
        textData = (EditText) view.findViewById(R.id.textDataD);
        mData = (Button) view.findViewById(R.id.dataD);
        mValor = (EditText) view.findViewById(R.id.valorD);
        cadastrarD = (Button) view.findViewById(R.id.cadastrarD);
        progressBar = (ProgressBar) view.findViewById(R.id.pbD);
        gerarMascaras();
    }
}
