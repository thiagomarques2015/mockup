package br.net.mockup.control.interfaces;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import br.net.mockup.R;

/**
 * Created by Thiago on 15/10/2015.
 */
class ChoicePhoto extends MultChoice {

    private static ChoicePhoto instance = new ChoicePhoto();

    private static final int GALERIA = 1;
    private static final int CAPTURAR_IMAGEM = 2;
    private static final String DIRETORIO = "Google";
    private String diretorio; // Diretorio
    private ImageListener onImageCreated; // Delegate da uri da imagem

    public static ChoicePhoto getInstance() {
        return instance;
    }

    private ChoicePhoto() {
    }

    @Override
    public AlertDialog create() {

        if(diretorio == null || diretorio.isEmpty())
            diretorio = DIRETORIO;

        final String[] items = {
                getActivity().getString(R.string.camera),
                getActivity().getString(R.string.galeria)
        };

        final Integer[] icons = {
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_menu_gallery
        };

        setDialogListener(dialogListener);
        setIcons(icons);
        setItems(items);

        return super.create();
    }

    public ChoicePhoto setOnImageCreated(ImageListener onImageCreated) {
        this.onImageCreated = onImageCreated;
        return this;
    }

    public ChoicePhoto setDiretorio(String diretorio) {
        this.diretorio = diretorio;
        return this;
    }

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case 0: // Camera
                    capturarImagem();
                    break;
                case 1: // Galeria
                    escolherImagemGaleria();
                    break;
            }
        }
    };

    private void capturarImagem() {
        Uri uri = InterfaceUtil.capturarCamera(diretorio);
        Intent intent = InterfaceUtil.criarIntentCapturarCamera(uri);

        if(onImageCreated != null)
            onImageCreated.onImageCreated(uri);

        try {
            getActivity().startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.complete_usando)), CAPTURAR_IMAGEM);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }

    private void escolherImagemGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(null, "image/*");

        getActivity().startActivityForResult(intent, GALERIA);
    }
}
