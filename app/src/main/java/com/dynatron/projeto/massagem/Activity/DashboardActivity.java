package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dynatron.projeto.massagem.Application.GerenteRegistros;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    GerenteRegistros gerenteRegistros;
    private GraphView graph, graph2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initViews();
    }

    private void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Dashboard");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        gerenteRegistros = (GerenteRegistros) getApplicationContext();

        graph = (GraphView) findViewById(R.id.graph01);
        graph2 = (GraphView) findViewById(R.id.graph02);

        initGraph01();
        initGraph02();
    }

    private void initGraph01() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(1, getValorMes(1)),
                new DataPoint(2, getValorMes(2)),
                new DataPoint(3, getValorMes(3)),
                new DataPoint(4, getValorMes(4)),
                new DataPoint(5, getValorMes(5)),
                new DataPoint(6, getValorMes(6)),
                new DataPoint(7, getValorMes(7)),
                new DataPoint(8, getValorMes(8)),
                new DataPoint(9, getValorMes(9)),
                new DataPoint(10, getValorMes(10)),
                new DataPoint(11, getValorMes(11)),
                new DataPoint(12, getValorMes(12))


        });
        series.setTitle("Grafico 1");
        series.setDrawDataPoints(true);
        series.setColor(R.color.colorPrimary);
        series.setDataPointsRadius(6);
        series.setThickness(4);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);
        graph.setTitle("Grafico 1 - Valor Toral Por Mês");
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);

        graph.addSeries(series);
    }

    private void initGraph02() {
        float receita = getReceita();
        float despesa = receita - gerenteRegistros.getValorTotal() ;
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(1, receita),
                new DataPoint(3, despesa),
        });
        series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (data.getX() == 1) {
                    return Color.rgb(46, 125, 50);
                } else {
                    return Color.rgb(216, 67, 21);
                }

            }
        });

        series2.setSpacing(10);

        series2.setDrawValuesOnTop(true);
        series2.setValuesOnTopColor(R.color.colorPrimary);

        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(4);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);

        graph2.setTitle("Grafico 2 - Diferença Entre Receita e Despesa");
        graph2.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        graph2.addSeries(series2);

        graph2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    if (value == 1) {
                        return "Receita";
                    } else if (value == 3) {
                        return "Despesa";
                    }

                    return "";


                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });

    }

    private float getValorMes(int mes) {
        float valor = 0;
        for (Registros r : gerenteRegistros.getRegistros()) {
            String[] dataArray = r.getData().toString().split("/");
            Log.d("dataArray", dataArray[1]);
            if (dataArray[1].equals(mes + "")) {
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

    private float getReceita() {
        float valor = 0;
        for (Registros r : gerenteRegistros.getRegistros()) {
            if (r.getTipo().toString().equals("R")) {
                valor += Float.parseFloat(r.getValor());
            }
        }
        return valor;
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
        return super.onOptionsItemSelected(item);
    }
}
