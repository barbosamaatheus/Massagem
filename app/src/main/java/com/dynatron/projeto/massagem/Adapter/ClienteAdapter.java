package com.dynatron.projeto.massagem.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Activity.ClientesActivity;
import com.dynatron.projeto.massagem.Interface.RecyclerViewOnClickListener;
import com.dynatron.projeto.massagem.Objetos.Cliente;
import com.dynatron.projeto.massagem.R;

import java.util.List;

/**
 * Created by User on 16/07/2018.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder>{

    private List<Cliente> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public ClienteAdapter(ClientesActivity clientesActivity, List<Cliente> clientes) {
        this.mList = clientes;
        mLayoutInflater = (LayoutInflater) clientesActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        this.mRecyclerViewOnClickListener = r;
    }

    @NonNull
    @Override
    public ClienteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.list_item_cliente, parent, false);
        ClienteAdapter.MyViewHolder mvh = new ClienteAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.MyViewHolder holder, int position) {
        holder.mNome.setText(mList.get(position).getNome().toString());
        holder.mTelefone.setText(mList.get(position).getTelefone().toString());
        holder.mNum.setText(mList.get(position).getNumTotal().toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNome, mTelefone, mNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            mNome = (TextView) itemView.findViewById(R.id.nome_cliente);
            mTelefone = (TextView) itemView.findViewById(R.id.telefone_cliente);
            mNum = (TextView) itemView.findViewById(R.id.numero_massagens);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getPosition());
            }

        }
    }


}