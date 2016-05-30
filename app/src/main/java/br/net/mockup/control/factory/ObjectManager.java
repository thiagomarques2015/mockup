package br.net.mockup.control.factory;

import android.content.Context;

import java.util.HashMap;

import br.net.mockup.Mockup;
import br.net.mockup.model.listener.PoolObject;
import br.net.mockup.model.pool.ObjectPool;

import static br.net.mockup.control.util.Printlog.info;

/**
 * Template para gerenciadores de objetos
 * Created by Thiago on 01/07/2015.
 */
public abstract class ObjectManager<T>{
    private final HashMap<String, T> objects;
    private final String nameObject;
    private ObjectPool objectPool;

    protected ObjectManager(Context context, MockupFactoryManager mockupFactoryManager, String nameObject) {
        this.nameObject = nameObject;

        if(!nameObject.isEmpty()){
            ObjectPoolFactory factory = mockupFactoryManager.getObjectPoolFactory();
            objectPool = factory.create(nameObject);
        }

        objects = new HashMap<>();
    }

    public boolean contains(String name){
        return objects.containsKey(name);
    }

    /**
     * Recupera a instancia do objeto pelo seu identificador
     * @param name identificador do objeto
     * @return
     */
    public final T get(String name){
        // Recupera do cache o objeto
        if(contains(name)){
            if(Mockup.getInstance().isDebug())
                info(String.format("Objeto '%s' reutilizado -> %s", toString(), name));
            return objects.get(name);
        }

        // Cria o objeto
        T obj = create();
        objects.put(name, obj);
        return obj;
    }

    /**
     * Cria um novo objeto sem salvar em memoria
     * @return instancia do novo objeto
     */
    @SuppressWarnings("unchecked")
    public T create(){
        if(nameObject.isEmpty()) throw new IllegalArgumentException("Nao existe uma piscina de objetos para esse gerenciador de objetos");
        return (T) objectPool.newObject();
    }

    /**
     * Salva o objeto em cache para ser reutilizado
     * @param name identificador do objeto
     * @param obj objeto que sera salvo
     */
    public final void save(String name, T obj){
        boolean isChanged = false;
        if(objects.containsKey(name)){
            // Avisa para todos os receptores que um objeto foi alterado
            isChanged = true;
        }

        objects.put(name, obj);

        if(isChanged)
            onChangedObject(obj);
    }

    /**
     * Libera o objeto da memoria
     * @param obj instancia do objeto que sera liberado
     */
    public final void free(PoolObject obj){
        if(nameObject.isEmpty()) throw new IllegalArgumentException("Nao existe uma piscina de objetos para esse gerenciador de objetos");
        objectPool.freeObject(obj);
    }

    protected abstract void onChangedObject(T obj);
}
