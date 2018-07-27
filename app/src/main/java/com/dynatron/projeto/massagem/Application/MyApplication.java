package com.dynatron.projeto.massagem.Application;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Activity.ExtratoActivity;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 11/07/2018.
 */

public class MyApplication extends Application {
    private List<Cliente> clientes;
    private List<Registros> registros;
    private FirebaseFirestore db;
    private List<String> messes;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void onCreate() {
        super.onCreate();
        clientes = new ArrayList<Cliente>();
        registros = new ArrayList<Registros>();
        db = FirebaseFirestore.getInstance();
    }

    public void writeRegistros(Registros r) {
        // Create a new user with a first and last name
        Map<String, Object> registro = new HashMap<>();
        registro.put("descricao", r.getDescricao());
        registro.put("data", r.getData());
        registro.put("valor", r.getValor());
        registro.put("tipo", r.getTipo());

        // Add a new document with a generated ID
        db.collection("reg")
                .add(registro)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void readRegistros() {

        db.collection("reg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            registros = new ArrayList<Registros>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Registros regs = new Registros();
                                regs.setDescricao(document.getString("descricao").toUpperCase());
                                regs.setData(document.getString("data"));
                                regs.setValor(document.getString("valor"));
                                regs.setTipo(document.getString("tipo"));
                                registros.add(regs);
                                Log.d("TAG", registros.toString());
                            }

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }

                    }
                });

    }

    public List<Registros> getRegistros() {
        Collections.sort(registros);
        return registros;
    }

    public void setRegistros(List<Registros> registros) {
        this.registros = registros;

    }

    public float getValorTotal() {
        float valorTotal = 0;
        for (Registros r : this.registros) {

            if (r.getTipo().toString().equals("R")) {
                String valor = r.getValor().replaceAll(",", ".");
                valorTotal += Float.parseFloat(valor);

            } else {
                String valor = r.getValor().replaceAll(",", ".");
                valorTotal -= Float.parseFloat(valor);
            }

        }
        return valorTotal;
    }

    public float getValorMes(int mes) {
        float valor = 0;
        for (Registros r : this.registros) {
            String[] dataArray = r.getData().toString().split("/");
            Log.d("dataArray", dataArray[1]);
            if (Integer.parseInt(dataArray[1]) == mes) {
                if (r.getTipo().toString().equals("R")) {
                    valor += Float.parseFloat(r.getValor());
                } else {
                    valor -= Float.parseFloat(r.getValor());
                }

                Log.d("dataArray", valor + "");
            }

        }
        return valor;
    }

    public float getReceita() {
        float valor = 0;
        for (Registros r : this.registros) {
            if (r.getTipo().toString().equals("R")) {
                valor += Float.parseFloat(r.getValor());
            }
        }
        return valor;
    }

    //FILTROS
    private List<Registros> buscarMes(String mes) {
        List<Registros> registrosDoMes = new ArrayList<Registros>();
        for (Registros registros : this.registros) {
            String[] dataSplit = registros.getData().split("/");
            String mesAno = dataSplit[1] + "/" + dataSplit[2];
            if (mes.equalsIgnoreCase(mesAno)) {
                registrosDoMes.add(registros);
            }
        }
        return registrosDoMes;

    }

    public List<String> getMesses() {
        messes = new ArrayList<String>();
        for (Registros registros : this.registros) {
            String[] dataSplit = registros.getData().split("/");
            String mesAno = dataSplit[1] + "/" + dataSplit[2];
            if (!messes.contains(mesAno)) {
                messes.add(mesAno);
            }
        }
        return messes;
    }

    public String getValorTMes(String mes) {
        float valor = 0;
        for (Registros registros : buscarMes(mes)) {
            if (registros.getTipo().equalsIgnoreCase("R")) {
                valor += Float.parseFloat(registros.getValor());
            } else if(registros.getTipo().equalsIgnoreCase("D")){
                valor -= Float.parseFloat(registros.getValor());
            }


        }
        return df.format(valor);
    }


    public String getQuant(String txt) {
        int quant = Integer.parseInt(getQuantDespesa(txt)) + Integer.parseInt(getQuantReceita(txt));
        return quant + "";

    }

    public String getQuantReceita(String txt) {
        int quant = 0;
        for (Registros registros : buscarMes(txt)) {
            if (registros.getTipo().equalsIgnoreCase("R")) {
                quant += 1;
            }

        }
        return quant + "";

    }

    public String getQuantDespesa(String txt) {
        int quant = 0;
        for (Registros registros : buscarMes(txt)) {
            if (registros.getTipo().equalsIgnoreCase("D")) {
                quant += 1;
            }

        }
        return quant + "";

    }

    public String getReceitaMes(String txt) {
        float quant = 0;
        for (Registros registros : buscarMes(txt)) {
            if (registros.getTipo().equalsIgnoreCase("R")) {
                quant += Float.parseFloat(registros.getValor());
            }
        }
        return df.format(quant);
    }

    public String getDespesaMes(String mes) {
        float quant = 0;
        for (Registros registros : buscarMes(mes)) {
            if (registros.getTipo().equalsIgnoreCase("D")) {
                quant -= Float.parseFloat(registros.getValor());
            }
        }
        return df.format(quant);
    }
    //CLIENTES

    public void writeClient(Cliente c) {
        // Create a new user with a first and last name
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", c.getNome());
        cliente.put("telefone", c.getTelefone());
        cliente.put("endereco", c.getEndereço());
        cliente.put("totalMassagens", c.getNumTotal());

        // Add a new document with a generated ID
        db.collection("cliente")
                .add(cliente)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

    }

    public void readClient() {
        db.collection("cliente")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            clientes = new ArrayList<Cliente>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cliente cliente = new Cliente();
                                cliente.setNome(document.getString("nome"));
                                cliente.setTelefone(document.getString("telefone"));
                                cliente.setEndereço(document.getString("endereco"));
                                cliente.setNumTotal(document.getString("totalMassagens"));
                                cliente.setId(document.getId());
                                clientes.add(cliente);
                                Log.d("TAG", document.getId() + " => " + document.getData() + " => " + "size:" + registros.size());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void editClient(Cliente c, String id) {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", c.getNome());
        cliente.put("telefone", c.getTelefone());
        cliente.put("endereco", c.getEndereço());
        cliente.put("totalMassagens", c.getNumTotal());

        db.collection("cliente").document(id)
                .set(cliente)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });

    }

    public void deleteClient(String id) {
        db.collection("cliente").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }

    public void editNumTotal(String nome, String value) {

        int valorM = 0;
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                valorM = Integer.parseInt(c.getNumTotal()) + Integer.parseInt(value);
                db.collection("cliente").document(buscarId(nome))
                        .update(
                                "totalMassagens", valorM + ""
                        );
            }
        }
    }

    public String buscarId(String nome) {
        String id = "";
        for (Cliente c : clientes) {
            if (c.getNome().toString().equals(nome)) {
                id = c.getId();
            }
        }
        return id;
    }

    public List<Cliente> getClientes() {
        Collections.sort(clientes);
        return this.clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public float getReceitaCliente(int position) {
        float valor = 0;

        for (Registros r : this.registros) {
            if (r.getTipo().toString().equals("R")) {
                if (r.getDescricao().equalsIgnoreCase(this.clientes.get(position).getNome())) {
                    valor += Float.parseFloat(r.getValor());
                }

            }
        }
        return valor;
    }

    public int getNumMassagnesCliente(int position) {
        return Integer.parseInt(this.clientes.get(position).getNumTotal());
    }


}
