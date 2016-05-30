package br.net.mockup.control.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import br.net.mockup.Mockup;
import br.net.mockup.model.listener.PoolObject;
import br.net.mockup.model.listener.PoolObjectFactory;

/**
 * Created by Thiago on 30/07/2015.
 */
public class GlideImagePoolFactory implements PoolObjectFactory {

    private CircleTransform circleTransform;

    public GlideImagePoolFactory() {
        circleTransform = new CircleTransform(Mockup.getInstance().getContext());
    }

    @Override
    public PoolObject createPoolObject() {
        Context context = Mockup.getInstance().getContext();
        RequestManager requestManager = Glide.with(context);
        // Cria o objeto Glide Image
        GlideImagePool glideImagePool = new GlideImagePool();
        glideImagePool.setCircleTransform(circleTransform);
        glideImagePool.setContext(context);
        glideImagePool.setRequestManager(requestManager);
        return glideImagePool;
    }
}
