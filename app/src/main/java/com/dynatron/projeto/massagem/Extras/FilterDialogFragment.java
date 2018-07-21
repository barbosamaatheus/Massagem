package com.dynatron.projeto.massagem.Extras;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.dynatron.projeto.massagem.R;

/**
 * Created by User on 21/07/2018.
 */

public class FilterDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.filter_layout, null))
                // Add action buttons
                .setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Limpar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}