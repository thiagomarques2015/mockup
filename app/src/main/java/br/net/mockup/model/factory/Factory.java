package br.net.mockup.model.factory;

import android.content.Context;

import java.util.HashMap;

import br.net.mockup.model.config.Constantes;
import br.net.mockup.model.listener.CreateFactory;
import br.net.mockup.model.manager.FactoryManager;
import br.net.mockup.model.pool.ObjectPool;
import br.net.mockup.model.pool.PropertiesPool;

import static br.net.mockup.control.util.Printlog.erro;
import static br.net.mockup.control.util.Printlog.warm;

/**
 * Base para criação de fábricas
 * Todas as fabricas concretas geradas so podem criar apenas um objeto reutilizavel
 * Created by Thiago on 03/06/2015.
 */
public abstract class Factory<T> implements CreateFactory {

    private ObjectPool poolProperties;
    public PropertiesPool apelidos;
    private HashMap<String, T> objects; // Matriz de objetos em cache

    public Factory() {
        objects = new HashMap<>();
    }

    /**
     * Carrega o arquivo de propriedades dentro de {Assets}
     * @param fileName { nome do arquivo }
     */
    public Factory(Context context, String fileName, FactoryManager factoryManager) {
        objects = new HashMap<>();
        try {
            poolProperties = factoryManager.getObjectPoolFactory().create(Constantes.Pool.PROPERTIES);
            apelidos = (PropertiesPool) poolProperties.newObject();
            apelidos.load(context.getAssets().open(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Libera as propiedades para ser utilizada novamente
     * por outro fragment
     */
    public void free(){
        if(apelidos != null)
            poolProperties.freeObject(apelidos);
    }

    @Override
    public T create(String name) {
        if(objects == null) throw new IllegalStateException("A lista de objetos nao foi iniciada em " + toString());
        T object = objects.get(name);
        if(object != null){
            warm("Objeto reutilizado : " + name);
            return object;
        }

        try {
            String stringClasse = apelidos.getProperty(name);
            Class classe = Class.forName(stringClasse);
            Object newObject = classe.newInstance();
            object = (T) newObject;
        } catch (Exception e) {
            /*if(BuildConfig.DEBUG_MODE)*/
                erro("Nao foi possivel criar => " + name);
            e.printStackTrace();
        }
        objects.put(name, object);
        return object;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
