package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Adapter.TabsAdapter;
import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Fragments.FinancasFragment;
import com.dynatron.projeto.massagem.Fragments.MassagensFragment;
import com.dynatron.projeto.massagem.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView saldo;
    private ActionMenuView amvMenu;
    private Toolbar myToolbar, mToolbarBottom;
    private MyApplication myApplication;
    private FragmentManager fm = getSupportFragmentManager();



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
                        intent = new Intent(MainActivity.this, ClientesActivity.class);
                        break;
                    case R.id.action_add:
                        intent = new Intent(MainActivity.this, CadastroActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_dashboard:
                        intent = new Intent(MainActivity.this, DashboardActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }

        });

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom_main, amvMenu.getMenu());


    }

    private void initViews() {
        myApplication = (MyApplication) getApplicationContext();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Registros");
        setSupportActionBar(myToolbar);

        saldo = (TextView) findViewById(R.id.saldo);
        amvMenu = (ActionMenuView) findViewById(R.id.amvMenu);
        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);

        initFragment();
        atualizarSaldo();
    }

    private void initFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        MassagensFragment massagensFragment = new MassagensFragment();
        ft.add(R.id.fragment_content, massagensFragment);
        ft.commit();
    }

    public void atualizarSaldo() {
        DecimalFormat df = new DecimalFormat("0.00");
        saldo.setText(df.format(myApplication.getValorTotal()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ic_replay_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_replay:
                restartActivity();
                break;
            case R.id.action_filter:
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, new FinancasFragment());
                ft.commit();

                break;

        }
        return super.onOptionsItemSelected(item);

    }

    public void restartActivity() {
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
        finish();

    }

}

