package br.net.mockup.model.listener;

/**
 * Created by Thiago on 03/06/2015.
 */
public interface PoolObjectFactory {
    /**
     * Creates a new object for the object pool.
     *
     * @return new object instance for the object pool
     */
    PoolObject createPoolObject();
}
