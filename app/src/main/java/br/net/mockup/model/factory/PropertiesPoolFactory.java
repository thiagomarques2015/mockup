package br.net.mockup.model.factory;


import br.net.mockup.model.listener.PoolObject;
import br.net.mockup.model.listener.PoolObjectFactory;
import br.net.mockup.model.pool.PropertiesPool;

/**
 * Created by Thiago on 03/06/2015.
 */
public class PropertiesPoolFactory implements PoolObjectFactory {
    @Override
    public PoolObject createPoolObject() {
        return new PropertiesPool();
    }
}
