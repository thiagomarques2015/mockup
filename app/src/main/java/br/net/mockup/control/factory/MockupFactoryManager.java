package br.net.mockup.control.factory;

import br.net.mockup.control.util.Printlog;

/**
 * Created by Thiago on 28/10/2015.
 */
public abstract class MockupFactoryManager {

    private ObjectPoolFactory objectPoolFactory;
    private Factory mainCommandFactory;

    public MockupFactoryManager() {
        Printlog.debug("=> Iniciando o gerenciador das fabricas");
        // Cria a fabrica de objetos reutilizaveis
        objectPoolFactory = new ObjectPoolFactory();
    }

    public MockupFactoryManager main(Factory mainCommandFactory){
        this.mainCommandFactory = mainCommandFactory;
        return this;
    }

    /**
     * Recupera a instancia da fabrica de objetos reutilizaveis
     * @return instancia da fabrica de objetos em piscina
     */
    public final ObjectPoolFactory getObjectPoolFactory() {
        return objectPoolFactory;
    }

    public final Factory getMainCommandFactory() {
        return mainCommandFactory;
    }
}
