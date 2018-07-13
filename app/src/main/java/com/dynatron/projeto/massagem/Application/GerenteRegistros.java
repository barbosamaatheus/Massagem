package com.dynatron.projeto.massagem.Application;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private FirebaseFirestore db;

    @Override
    public void onCreate() {
        super.onCreate();
        registros = new ArrayList<Registros>();
        db = FirebaseFirestore.getInstance();
        setRegistros(readFireStore());
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

        registros.add(r);
        RegistrosAdapter registrosAdapter = new RegistrosAdapter();
        registrosAdapter.addListItem(r, registros.size());
    }

    public List<Registros> readFireStore() {
        final List<Registros> registrosList = new ArrayList<Registros>();
        db.collection("reg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Registros regs = new Registros();
                                regs.setDescricao(document.getString("descricao"));
                                regs.setData(document.getString("data"));
                                regs.setValor(document.getString("valor"));
                                regs.setTipo(document.getString("tipo"));
                                registrosList.add(regs);
                                Log.d("TAG", document.getId() + " => " + document.getData() + " => " + "size:" + registros.size());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return registrosList;
    }

    public float getValorTotal() {
        float valorTotal = 0;
        for (Registros r : this.registros) {
            Log.d("TAG1", "for" + r.getTipo().toString());
            if (r.getTipo().toString().equals("R")) {
                valorTotal = valorTotal + Float.parseFloat(r.getValor());
                Log.d("TAG1", "+" + valorTotal);
            } else {
                valorTotal = valorTotal - Float.parseFloat(r.getValor());
                Log.d("TAG1", "-" + valorTotal);
            }

        }
        return valorTotal;
    }

    public List<Registros> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registros> registros) {
        this.registros = registros;

    }
}
