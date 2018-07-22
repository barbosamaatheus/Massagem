package com.dynatron.projeto.massagem.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.Toast;

import com.dynatron.projeto.massagem.Activity.DetalhesClienteActivity;
import com.dynatron.projeto.massagem.Activity.ExtratoActivity;
import com.dynatron.projeto.massagem.Activity.MainActivity;
import com.dynatron.projeto.massagem.Adapter.ExtratoAdapter;
import com.dynatron.projeto.massagem.Adapter.RegistrosAdapter;
import com.dynatron.projeto.massagem.Application.MyApplication;
import com.dynatron.projeto.massagem.Interface.RecyclerViewOnClickListener;
import com.dynatron.projeto.massagem.R;

import java.util.ArrayList;
import java.util.List;


public class FinancasFragment extends Fragment implements RecyclerViewOnClickListener {
    private MyApplication myApplication;
    private RecyclerView mRecyclerView;
    private ExtratoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> datas = new ArrayList<String>();

    public FinancasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financas, container, false);
        initViews(view);

        this.datas = getMesses();

        mAdapter = new ExtratoAdapter(getActivity(), this.datas);
        mAdapter .setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    private void initViews(View view) {
        List<String> messes = new ArrayList<String>();
        myApplication =(MyApplication) getActivity().getApplicationContext();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public List<String> getMesses() {
        return myApplication.getMesses();
    }

    @Override
    public void onClickListener(View view, int positon) {
        Intent intent = new Intent(getActivity(), ExtratoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("txt", datas.get(positon));
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
