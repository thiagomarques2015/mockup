package br.net.mockup.control.factory;

import java.util.Properties;

import br.net.mockup.model.listener.PoolObject;

/**
 * Created by Thiago on 03/06/2015.
 */
class PropertiesPool extends Properties implements PoolObject {
    @Override
    public void initializePoolObject() {
        clear();
    }

    @Override
    public void finalizePoolObject() {

    }
}
