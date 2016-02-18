package br.net.mockup.model.factory;

import java.util.HashMap;

import br.net.mockup.model.config.Constantes;
import br.net.mockup.model.pool.ObjectPool;

/**
 * Piscina de objetos reutilizaveis
 * Created by Thiago on 03/06/2015.
 */
public class ObjectPoolFactory extends Factory<ObjectPool> {

    private HashMap<String, Object> pools;

    public ObjectPoolFactory() {
        super();
        pools = new HashMap<>();
        // Iniciar os objetos pools da aplicacao
        pools.put(Constantes.Pool.PROPERTIES, new ObjectPool(new PropertiesPoolFactory(), 1));
        //pools.put(Constantes.Pool.REQUEST, new ObjectPool(new RequestPoolFactory(), 5)); // No maximo 5 objetos conexao reusaveis
        pools.put(Constantes.Pool.GLIDE_IMAGE, new ObjectPool(new GlideImagePoolFactory(), 200)); // Maximo de imagens com a biblioteca glide
    }

    /**
     * Adiciona uma nova piscina na lista de piscina de objetos
     * @param key nome da piscina
     * @param objectPool piscina de objetos
     * @return instancia de ObjectPoolFactory
     */
    public final ObjectPoolFactory add(String key, Object objectPool){
        if(pools == null) throw new IllegalStateException("pool nao pode ser nulo para adicioanr uma nova piscina");
        pools.put(key, objectPool);
        return this;
    }

    @Override
    public ObjectPool create(String name) {

        if(pools.containsKey(name)){
            return (ObjectPool) pools.get(name);
        }

        return null;
    }
}
