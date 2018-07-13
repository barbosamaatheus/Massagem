package com.dynatron.projeto.massagem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Registros> registros;
    private FirebaseFirestore db;
    private TextView saldo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GerenteRegistros gerenteRegistros = (GerenteRegistros) getApplicationContext();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Massagem");
        setSupportActionBar(myToolbar);

        registros = new ArrayList<Registros>();
        saldo = (TextView) findViewById(R.id.saldo);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RegistrosAdapter(this, gerenteRegistros.getRegistros());
        mRecyclerView.setAdapter(mAdapter);

        atualizarSaldo();

    }

    public void atualizarSaldo() {
        mAdapter.notifyDataSetChanged();
        GerenteRegistros gerenteRegistros = (GerenteRegistros) getApplicationContext();
        saldo.setText(""+gerenteRegistros.getValorTotal());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ic_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
                break;
            case R.id.action_replay:
                mAdapter.notifyDataSetChanged();
                atualizarSaldo();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}

