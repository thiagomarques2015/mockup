package br.net.mockup.control.glide;

import android.net.Uri;

import br.net.mockup.model.pool.ObjectPool;


/**
 * Interface para abrir uma imagem com GLIDE
 * Created by Thiago on 05/08/2015.
 */
public class GlideFacade {

    /**
     * Abrir uma imagem recuperando da piscina de objetos a imagem
     * @param factory fabrica de criacao de imagens
     * @param photo imagem ( URL, URI ou Recurso )
     * @return requisicao da imagem
     */
    public static GlideImage open(ObjectPool factory, Object photo){
        GlideImagePool image = (GlideImagePool) factory.newObject();

        if(photo instanceof String)
            image = (GlideImagePool) image.load((String) photo);
        else if(photo instanceof Uri)
            image = (GlideImagePool) image.load((Uri) photo);
        else if(photo instanceof Integer)
            image = (GlideImagePool) image.load((int) photo);

        return image.factory(image, factory);
    }
}
