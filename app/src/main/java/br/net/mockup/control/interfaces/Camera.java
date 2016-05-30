package br.net.mockup.control.interfaces;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import br.net.mockup.R;

/**
 * Created by Thiago on 29/10/2015.
 */
class Camera extends Interface<Object> {
    private static Camera ourInstance = new Camera();

    private static final String DIRETORIO = "Google";
    private String diretorio; // Diretorio
    private ImageListener onImageCreated; // Delegate da uri da imagem

    public static Camera getInstance() {
        return ourInstance;
    }

    private Camera() {
    }

    @Override
    public Object create() {

        if(diretorio == null || diretorio.isEmpty())
            diretorio = DIRETORIO;

        return null;
    }

    public Camera setDiretorio(String diretorio) {
        this.diretorio = diretorio;
        return this;
    }

    public void capturarImagem() {
        Uri uri = InterfaceUtil.capturarCamera(diretorio);
        Intent intent = InterfaceUtil.criarIntentCapturarCamera(uri);

        if(onImageCreated != null)
            onImageCreated.onImageCreated(uri);

        try {
            getActivity().startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.complete_usando)), InterfaceUtil.CAPTURAR_IMAGEM);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }

    public Camera setOnImageCreated(ImageListener onImageCreated) {
        this.onImageCreated = onImageCreated;
        return this;
    }
}
