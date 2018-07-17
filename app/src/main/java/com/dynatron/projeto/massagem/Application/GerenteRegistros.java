package com.dynatron.projeto.massagem.Application;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 11/07/2018.
 */

public class GerenteRegistros extends Application {

    private List<Registros> registros;
    private List<Cliente> clientes;
    private FirebaseFirestore db;
  // Registros
    @Override
    public void onCreate() {
        super.onCreate();
        registros = new ArrayList<Registros>();
        clientes = new ArrayList<Cliente>();
        db = FirebaseFirestore.getInstance();
    }

    public void writeFireStore(Registros r) {
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

    public void readFireStore() {

        db.collection("reg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            registros = new ArrayList<Registros>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Registros regs = new Registros();
                                regs.setDescricao(document.getString("descricao"));
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

    public List<Registros> getRegistros() {
        //readFireStore();
        return registros;
    }

    public void setRegistros(List<Registros> registros) {
        this.registros = registros;

    }


  // Clientes

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

    public void readCliente() {
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
                                clientes.add(cliente);
                                Log.d("TAG", document.getId() + " => " + document.getData() + " => " + "size:" + registros.size());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
