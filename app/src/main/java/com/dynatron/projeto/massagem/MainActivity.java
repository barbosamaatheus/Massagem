package com.dynatron.projeto.massagem;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Runnable {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView saldo;
    private ActionMenuView amvMenu;
    private Toolbar myToolbar, mToolbarBottom;
    private GerenteRegistros gerenteRegistros;
    private ProgressBar mPb_main;
    private static final long delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.action_contatos:
                        //intent = new Intent(MainActivity.this, SobreActivity.class);
                        break;
                    case R.id.action_add:
                        intent = new Intent(MainActivity.this, CadastroActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_filtrar:
                        ///intent = new Intent(MainActivity.this, ReservaActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }

        });

        Handler h = new Handler();
        h.postDelayed(this, delay);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom_main, amvMenu.getMenu());


    }

    private void initViews() {
        gerenteRegistros = (GerenteRegistros) getApplicationContext();
        mAdapter = new RegistrosAdapter(this, gerenteRegistros.getRegistros());

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Massagem");
        setSupportActionBar(myToolbar);

        saldo = (TextView) findViewById(R.id.saldo);
        amvMenu = (ActionMenuView) findViewById(R.id.amvMenu);
        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);
        mPb_main = (ProgressBar) findViewById(R.id.pb_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    public void atualizarSaldo() {
        DecimalFormat df = new DecimalFormat("0.00");
        saldo.setText(df.format(gerenteRegistros.getValorTotal()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ic_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_replay:
                restartActivity();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void run() {
        mRecyclerView.setAdapter(mAdapter);
        mPb_main.setVisibility(View.GONE);
        atualizarSaldo();
    }

    public void restartActivity(){
        finish();
        startActivity(getIntent());
        mPb_main.setVisibility(View.VISIBLE);
        atualizarSaldo();

    }
}

