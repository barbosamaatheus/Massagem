package com.dynatron.projeto.massagem.Adapter;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dynatron.projeto.massagem.Fragments.DespesaFragment;
import com.dynatron.projeto.massagem.Fragments.ReceitaFragment;


/**
 * Created by viniciusthiengo on 5/18/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"Receita","Despesa"};

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        mContext = c;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        if (position == 0){
            frag = new ReceitaFragment();
        }
        else if (position == 1) { // ALL CARS
            frag = new DespesaFragment();
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
