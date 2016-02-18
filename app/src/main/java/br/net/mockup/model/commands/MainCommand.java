package br.net.mockup.model.commands;

import android.content.Context;

import br.net.mockup.model.ImageParcelable;

/**
 * Created by Thiago on 30/10/2015.
 */
public abstract class MainCommand implements Command {
    private Context context; // Contexto da aplicacao
    private ImageParcelable image; // reader da imagem gerada pelo Lolipop

    public MainCommand setContext(Context context) {
        this.context = context;
        return this;
    }

    protected Context getContext() {
        return context;
    }

    public MainCommand setImage(ImageParcelable image) {
        this.image = image;
        return this;
    }

    protected ImageParcelable getImage() {
        return image;
    }
}
