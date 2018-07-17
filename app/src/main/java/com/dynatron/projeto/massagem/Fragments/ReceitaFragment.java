package com.dynatron.projeto.massagem.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Extras.MoneyTextWatcher;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;
import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.firestore.Query;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ReceitaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText mDescricao,  mValor, textData;
    private Button mData, cadastrarM;

    private ProgressBar progressBar;

    public ReceitaFragment() {

    }

    private List<Registros> registros = new ArrayList<Registros>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receita, container, false);

        initViews(view);

        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        cadastrarM.setOnClickListener(new View.OnClickListener() {
            GerenteRegistros gr = (GerenteRegistros) getActivity().getApplicationContext();
            @Override
            public void onClick(View v) {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String desc = mDescricao.getText().toString();
                    String data = textData.getText().toString();
                    String valor = mValor.getText().toString();
                            //.substring(2);
                    Registros r = new Registros(desc, data, valor);
                    r.setTipo("R");
                    Log.d("SubString", valor);
                    gr.writeFireStore(r);

                    Toast toast = Toast.makeText(getActivity(), "Cadastrado Com Sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity(), "Erro! Tente novamente.", Toast.LENGTH_SHORT);
                    toast.show();

                } finally {
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
    private void gerarMascaras() {
        //mValor.addTextChangedListener(new MoneyTextWatcher(mValor));
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

    private void initViews(View view) {
        mDescricao = (EditText) view.findViewById(R.id.cliente);
        mData = (Button) view.findViewById(R.id.dataM);
        mValor = (EditText) view.findViewById(R.id.valorM);
        cadastrarM = (Button) view.findViewById(R.id.cadastrarM);
        progressBar = (ProgressBar) view.findViewById(R.id.pbR);
        textData = (EditText) view.findViewById(R.id.textData);
        //gerarMascaras();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        textData.setText(date);
    }

}
