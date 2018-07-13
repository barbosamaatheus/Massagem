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
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;



public class DespesaFragment extends Fragment {
    private ProgressBar progressBar;
    private EditText mDescricao, mData, mValor;
    private Button cadastrarD;


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

        mDescricao = (EditText) view.findViewById(R.id.descricaoD);
        mData = (EditText) view.findViewById(R.id.dataD);
        mValor = (EditText) view.findViewById(R.id.valorD);
        cadastrarD = (Button) view.findViewById(R.id.cadastrarD);
        progressBar = (ProgressBar) view.findViewById(R.id.pbD);

        cadastrarD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progressBar.setVisibility(View.VISIBLE);
                    String desc = mDescricao.getText().toString();
                    String data = mData.getText().toString();
                    String valor = mValor.getText().toString();
                    Registros r = new Registros(desc, data, valor);
                    r.setTipo("D");
                    GerenteRegistros gr = (GerenteRegistros) getActivity().getApplicationContext();
                    gr.writeFireStore(r);
                    Toast toast = Toast.makeText(getActivity(), "Cadastrado Com Sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception e){
                    Toast toast = Toast.makeText(getActivity(), "Erro! Tente novamente.", Toast.LENGTH_SHORT);
                    toast.show();
                }finally {
                    mDescricao.setText("");
                    mData.setText("");
                    mValor.setText("");
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }


}
