package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dynatron.projeto.massagem.Adapter.ClienteAdapter;
import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Interface.RecyclerViewOnClickListener;
import com.dynatron.projeto.massagem.R;

public class ClientesActivity extends AppCompatActivity implements RecyclerViewOnClickListener{
    private Toolbar myToolbar;
    private RecyclerView mRecyclerView;
    private ClienteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GerenteRegistros gerenteRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        initViews();
    }

    public void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_cliente);
        myToolbar.setTitle("Clientes");
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        gerenteRegistros = (GerenteRegistros) getApplicationContext();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_cliente);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ClienteAdapter(this, gerenteRegistros.getClientes());
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ic_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        if (id == android.R.id.home) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_add) {
            intent = new Intent(getApplicationContext(), CadClienteActivity.class);
            startActivity(intent);
            finish();
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClickListener(View view, int positon) {
        Intent intent = new Intent(getApplicationContext(), DetalhesClienteActivity.class);
        startActivity(intent);
        finish();
    }
}


