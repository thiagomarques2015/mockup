package br.net.mockup.model.listener;

/**
 * Created by Thiago on 03/06/2015.
 */
public interface PoolObject {
    /**
     * Initialization method. Called when an object is retrieved
     * from the object pool or has just been created.
     */
    void initializePoolObject();

    /**
     * Finalization method. Called when an object is stored in
     * the object pool to mark it as free.
     */
    void finalizePoolObject();
}
