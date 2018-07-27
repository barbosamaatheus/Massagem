package com.dynatron.projeto.massagem.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ExtratoActivity extends AppCompatActivity {
    private TextView mMes, mQuantR, mReceita, mQuantD, mDespesa, mQuant;
    private TextView mTotal;
    private MyApplication myApplication;
    private Toolbar myToolbar;
    private String txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        txt = bundle.getString("txt");


        initViews();

        myToolbar.setTitle("Extrato");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setCampos(txt);
    }

    private void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myApplication = (MyApplication) getApplicationContext();
        mMes = (TextView) findViewById(R.id.dataExtrato);
        mQuantR = (TextView) findViewById(R.id.quantRExtrato);
        mReceita = (TextView) findViewById(R.id.receitaExtrato);
        mQuantD = (TextView) findViewById(R.id.quantDExtrato);
        mDespesa = (TextView) findViewById(R.id.despesaExtrato);
        mQuant = (TextView) findViewById(R.id.quantExtrato);
        this.mTotal = (TextView) findViewById(R.id.totalE);
    }

    private void setCampos(String txt) {
        mMes.setText(txt);
        mQuant.setText(myApplication.getQuant(txt));
        mQuantR.setText(myApplication.getQuantReceita(txt));
        mReceita.setText(myApplication.getReceitaMes(txt));
        mQuantD.setText(myApplication.getQuantDespesa(txt));
        mDespesa.setText(myApplication.getDespesaMes(txt));
        this.mTotal.setText(myApplication.getValorTMes(txt));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.extrato_menu, menu);
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
        if (id == R.id.action_export) {
            String filename = "extrato.jpg";
            Bitmap b  = createBitmapFromLayout();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File file = new File(this.getFilesDir(), filename);
            FileOutputStream outputStream = null;

            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file);
                outputStream.write(bytes.toByteArray());
                outputStream.close();
                Toast.makeText(getApplicationContext(),
                        file.getAbsolutePath(),
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap createBitmapFromLayout() {
        LinearLayout view = (LinearLayout)findViewById(R.id.layout_extrato);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        /*Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width,
                v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);*/
        return bm;
    }

}