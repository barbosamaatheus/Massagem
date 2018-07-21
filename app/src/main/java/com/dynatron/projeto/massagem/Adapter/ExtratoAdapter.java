package com.dynatron.projeto.massagem.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynatron.projeto.massagem.Interface.RecyclerViewOnClickListener;
import com.dynatron.projeto.massagem.Objetos.Registros;

import java.util.List;

import static com.dynatron.projeto.massagem.R.drawable;
import static com.dynatron.projeto.massagem.R.id;
import static com.dynatron.projeto.massagem.R.layout;


public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.MyViewHolder>{
    private List<String> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public ExtratoAdapter() {

    }
    public ExtratoAdapter(Context c, List<String> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        this.mRecyclerViewOnClickListener = r;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(layout.list_mes, viewGroup, false);
        ExtratoAdapter.MyViewHolder mvh = new ExtratoAdapter.MyViewHolder(v);
        return mvh;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.mTitulo.setText(mList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitulo;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);
            mTitulo = (TextView) itemView.findViewById(id.titulo);
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