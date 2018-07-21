package com.dynatron.projeto.massagem.Adapter;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dynatron.projeto.massagem.Fragments.DespesaFragment;
import com.dynatron.projeto.massagem.Fragments.FinancasFragment;
import com.dynatron.projeto.massagem.Fragments.MassagensFragment;
import com.dynatron.projeto.massagem.Fragments.ReceitaFragment;


/**
 * Created by viniciusthiengo on 5/18/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles;
    private String tipo;
    public TabsAdapter(FragmentManager fm, Context c, String [] titles, String tipo) {
        super(fm);
        mContext = c;
        this.titles = titles;
        this.tipo = tipo;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        if(tipo.equalsIgnoreCase("cadastro")){
            if (position == 0){
                frag = new ReceitaFragment();
            }
            else if (position == 1) { // ALL CARS
                frag = new DespesaFragment();
            }
        } else if(this.tipo.equalsIgnoreCase("registros")){
            if (position == 0){
                frag = new MassagensFragment();
            }
            else if (position == 1) { // ALL CARS
                frag = new FinancasFragment();
            }
        }

//

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        /*Drawable d = mContext.getResources().getDrawable( icons[position] );
        d.setBounds(0, 0, heightIcon, heightIcon);

        ImageSpan is = new ImageSpan( d );

        SpannableString sp = new SpannableString(" ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );


        return ( sp );*/
        return (titles[position]);
    }
}
