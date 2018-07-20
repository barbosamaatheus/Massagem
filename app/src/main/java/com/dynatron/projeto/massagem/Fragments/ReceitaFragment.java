package com.dynatron.projeto.massagem.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Activity.MainActivity;
import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Extras.MoneyTextWatcher;
import com.dynatron.projeto.massagem.Objetos.Cliente;
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
    private EditText mValor, textData;
    private Spinner mDescricao;
    private Button cadastrarM;
    private ImageButton mData;
    private GerenteRegistros gr;


    public ReceitaFragment() {

    }


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

            @Override
            public void onClick(View v) {
                String desc = mDescricao.getSelectedItem().toString();
                String data = textData.getText().toString();
                String valor = mValor.getText().toString();
                try {
                   if (!validarCampos(desc, data, valor)){
                       Registros r = new Registros(desc, data, valor);
                       r.setTipo("R");
                       gr.writeFireStore(r);
                       gr.editNumTotal(desc, "1");
                       alertDialog("Cadastrado Com Sucesso!", "Novo Cadastro", "Voltar p/ Registros");
                   }

                } catch (Exception e) {
                    alertDialog("Erro ao Cadastrar! \n Verifique se preencheu todos os campos e tente novamente",
                            "Tentar Novamente", "Voltar p/ Registros");
                    /*Toast toast = Toast.makeText(getActivity(), "Erro! Tente novamente.", Toast.LENGTH_SHORT);
                    toast.show();*/

                } finally {
                    gr.readFireStore();
                    gr.readCliente();
                }
            }
        });
        return view;
    }

    private void gerarMascaras() {
        mValor.addTextChangedListener(new MoneyTextWatcher(mValor));

    }

    public List<String> getListaNomeClientes() {
        List<String> nomeClientes = new ArrayList<String>();
        nomeClientes.add("Nome do Cliente");
        List<Cliente> clientes = gr.getClientes();
        for (Cliente c : clientes) {
            nomeClientes.add(c.getNome().toString());
        }
        return nomeClientes;
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

    @SuppressLint("NewApi")
    private void initViews(View view) {
        gr = (GerenteRegistros) getActivity().getApplicationContext();
        mData = (ImageButton) view.findViewById(R.id.dataM);
        mValor = (EditText) view.findViewById(R.id.valorM);
        cadastrarM = (Button) view.findViewById(R.id.cadastrarM);
        textData = (EditText) view.findViewById(R.id.textData);
        mDescricao = (Spinner) view.findViewById(R.id.cliente);

        List<String> categories = getListaNomeClientes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {                    // Disable the second item from Spinner
                    return false;
                } else {
                    return true;
                }
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDescricao.setAdapter(adapter);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        textData.setText(editarData(date));
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
                        mDescricao.setSelected(false);
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
