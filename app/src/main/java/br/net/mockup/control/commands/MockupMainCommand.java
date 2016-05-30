package br.net.mockup.control.commands;

import android.content.Context;

import br.net.mockup.model.ImageParcelable;
import br.net.mockup.model.commands.Command;

/**
 * Created by Thiago on 30/10/2015.
 */
public abstract class MockupMainCommand implements Command {
    private Context context; // Contexto da aplicacao
    private ImageParcelable image; // reader da imagem gerada pelo Lolipop

    public MockupMainCommand setContext(Context context) {
        this.context = context;
        return this;
    }

    protected Context getContext() {
        return context;
    }

    public MockupMainCommand setImage(ImageParcelable image) {
        this.image = image;
        return this;
    }

    protected ImageParcelable getImage() {
        return image;
    }
}
