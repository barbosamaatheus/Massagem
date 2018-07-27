package com.dynatron.projeto.massagem.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private MyApplication myApplication;
    private GraphView graph, graph2, graph3, graph4;


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

        myApplication = (MyApplication) getApplicationContext();

        graph = (GraphView) findViewById(R.id.graph01);
        graph2 = (GraphView) findViewById(R.id.graph02);
        graph3 = (GraphView) findViewById(R.id.graph03);
        graph4 = (GraphView) findViewById(R.id.graph04);

        initGraph01();
        initGraph02();
        initGraph03();
        initGraph04();
    }

    private void initGraph01() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(1, myApplication.getValorMes(1)),
                new DataPoint(2, myApplication.getValorMes(2)),
                new DataPoint(3, myApplication.getValorMes(3)),
                new DataPoint(4, myApplication.getValorMes(4)),
                new DataPoint(5, myApplication.getValorMes(5)),
                new DataPoint(6, myApplication.getValorMes(6)),
                new DataPoint(7, myApplication.getValorMes(7)),
                new DataPoint(8, myApplication.getValorMes(8)),
                new DataPoint(9, myApplication.getValorMes(9)),
                new DataPoint(10, myApplication.getValorMes(10)),
                new DataPoint(11, myApplication.getValorMes(11)),
                new DataPoint(12, myApplication.getValorMes(12))


        });
        setLineSeriesDefault(series, R.color.colorAccent);
        editGraph(graph, "Grafico 1 - Valor Total Por Mês", 1, 12);

        graph.addSeries(series);
    }

    private void initGraph02() {

        float receita = myApplication.getReceita();
        float despesa = receita - myApplication.getValorTotal();
        float valorTotal = receita + despesa;

        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(1, calculaPorCento(valorTotal, receita)),
                new DataPoint(3, calculaPorCento(valorTotal, despesa)),
        });
        editColorBarSeries(series2);
        series2.setSpacing(10);
        series2.setDrawValuesOnTop(true);
        series2.setValuesOnTopColor(R.color.colorPrimary);

        editGraph(graph2, "Grafico 2 - Diferença Entre Receita e Despesa (%)", 0, 4);

        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(100);
        editLabelBarGraph(graph2);
        graph2.addSeries(series2);

    }

    private void initGraph03() {
        //Linha1
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(createDataPointsClientes());
        setLineSeriesDefault(series3, R.color.colorPrimary);
        editGraph(graph3, "Grafico 3 - Quantidade  por Cliente", 0, myApplication.getClientes().size());
        editLabelLineGraph(graph3);
        graph3.addSeries(series3);
    }

    private void initGraph04() {
        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(createDataPointsClientes2());
        setLineSeriesDefault(series4, R.color.colorAccent);
        editGraph(graph4, "Grafico 4 - Receita por Cliente", 0, myApplication.getClientes().size());
        editLabelLineGraph(graph4);
        graph4.addSeries(series4);
    }

    private DataPoint[] createDataPointsClientes() {
        DataPoint[] dataPoints = new DataPoint[myApplication.getClientes().size()];
        for (int i = 0; i < myApplication.getClientes().size(); i++) {
            dataPoints[i] = new DataPoint(i, myApplication.getNumMassagnesCliente(i));
        }
        return dataPoints;
    }

    private DataPoint[] createDataPointsClientes2() {
        DataPoint[] dataPoints = new DataPoint[myApplication.getClientes().size()];
        for (int i = 0; i < myApplication.getClientes().size(); i++) {
            dataPoints[i] = new DataPoint(i, myApplication.getReceitaCliente(i));
        }
        return dataPoints;
    }

    private float calculaPorCento(float vt, float valor) {
        return valor / (vt / 100);
    }

    private void editGraph(GraphView labels, String s, int min, int max) {
        labels.getViewport().setXAxisBoundsManual(true);
        labels.getViewport().setMinX(min);
        labels.getViewport().setMaxX(max);
        labels.getViewport().setScalableY(true);
        labels.getViewport().setScrollableY(true);
        labels.setTitle(s);
    }

    private void editLabelBarGraph(GraphView graph2) {
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

    private void editColorBarSeries(BarGraphSeries<DataPoint> series2) {
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
    }

    private void editLabelLineGraph(GraphView labels) {
        labels.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    for (int i = 0; i < myApplication.getClientes().size(); i++) {
                        int j = i / 2;
                        if (value == i) {
                            return myApplication.getClientes().get(i).getNome().substring(0, 3);
                        }
                        if (value == j) {
                            return myApplication.getClientes().get(j).getNome().substring(0, 2);
                        }

                    }
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
                return "--";
            }
        });
    }

    public void setLineSeriesDefault(LineGraphSeries<DataPoint> seriesDefaut, int color) {
        seriesDefaut.setDrawDataPoints(true);
        seriesDefaut.setColor(color);
        seriesDefaut.setDataPointsRadius(6);
        seriesDefaut.setThickness(4);
    }

   /* private String[] gerarLabels() {
        String[] labels = new String[myApplication.getClientes().size()];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = myApplication.getClientes().get(i).getNome().substring(0, 1);
        }
        return labels;
    }
*/
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

