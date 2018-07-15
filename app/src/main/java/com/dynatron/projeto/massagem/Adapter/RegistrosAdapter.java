package com.dynatron.projeto.massagem.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Interface.RecyclerViewOnClickListener;
import com.dynatron.projeto.massagem.Objetos.Registros;
import com.dynatron.projeto.massagem.R;

import java.util.List;

import static com.dynatron.projeto.massagem.R.*;
import static com.dynatron.projeto.massagem.R.color.colorDespesa;
import static com.dynatron.projeto.massagem.R.drawable.*;

/**
 * Created by User on 11/07/2018.
 */

public class RegistrosAdapter extends RecyclerView.Adapter<RegistrosAdapter.MyViewHolder> {
    private List<Registros> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public RegistrosAdapter() {

    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        mRecyclerViewOnClickListener = r;
    }


    public RegistrosAdapter(Context c, List<Registros> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void addListItem(Registros s, int position) {
        mList.add(s);
        notifyItemInserted(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(layout.list_item, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.mTitulo.setText(mList.get(position).getDescricao());
        myViewHolder.mData.setText(mList.get(position).getData());
        myViewHolder.mValor.setText(mList.get(position).getValor());
        if (mList.get(position).getTipo().equals("D")) {
            myViewHolder.mTipo.setImageResource(drawable.down_ic);
        }else {
            myViewHolder.mTipo.setImageResource(drawable.up_icon);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitulo, mData, mValor;
        public ImageView mTipo;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);
            mTitulo = (TextView) itemView.findViewById(id.titulo);
            mData = (TextView) itemView.findViewById(id.descricaoD);
            mValor = (TextView) itemView.findViewById(id.preco);
            mTipo = (ImageView) itemView.findViewById(id.tipo);



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