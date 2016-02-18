package br.net.mockup.model.pool;

import br.net.mockup.control.glide.GlideImage;
import br.net.mockup.model.listener.PoolObject;

/**
 * Created by Thiago on 30/07/2015.
 */
public class GlideImagePool extends GlideImage implements PoolObject {

    @Override
    public void initializePoolObject() {
        initialize();
    }

    @Override
    public void finalizePoolObject() {

    }
}
