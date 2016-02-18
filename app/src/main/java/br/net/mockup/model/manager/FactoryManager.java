package br.net.mockup.model.manager;

import br.net.mockup.control.util.Printlog;
import br.net.mockup.model.factory.Factory;
import br.net.mockup.model.factory.ObjectPoolFactory;

/**
 * Created by Thiago on 28/10/2015.
 */
public abstract class FactoryManager {

    private ObjectPoolFactory objectPoolFactory;
    private Factory mainCommandFactory;

    public FactoryManager() {
        Printlog.debug("=> Iniciando o gerenciador das fabricas");
        // Cria a fabrica de objetos reutilizaveis
        objectPoolFactory = new ObjectPoolFactory();
    }

    public FactoryManager main(Factory mainCommandFactory){
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
