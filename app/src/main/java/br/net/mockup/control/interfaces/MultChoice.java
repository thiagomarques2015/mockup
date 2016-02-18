package br.net.mockup.control.interfaces;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ListAdapter;

import br.net.mockup.R;

/**
 * Created by Thiago on 15/10/2015.
 */
public abstract class MultChoice extends Interface<AlertDialog> {

    private DialogInterface.OnClickListener dialogListener;
    private String[] items;
    private Integer[] icons;

    protected void setDialogListener(DialogInterface.OnClickListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    protected void setItems(String[] items) {
        this.items = items;
    }

    protected void setIcons(Integer[] icons) {
        this.icons = icons;
    }

    @Override
    public AlertDialog create() {
        ListAdapter listAdapter = new ArrayAdapterWithIcon(getActivity(), items, icons);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.opcoes);
        builder.setAdapter(listAdapter, dialogListener);
        return builder.create();
    }
}
