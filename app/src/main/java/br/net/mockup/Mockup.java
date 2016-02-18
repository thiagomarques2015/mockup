package br.net.mockup;

import android.content.Context;

import br.net.mockup.model.manager.FactoryManager;

/**
 * Created by Thiago on 10/11/2015.
 */
public class Mockup {
    private static Mockup ourInstance = new Mockup();

    private FactoryManager factoryManager;
    private Context context;
    private boolean debug;
    private boolean monitorTasks;

    public static Mockup getInstance() {
        return ourInstance;
    }

    private Mockup() {
    }

    public Context getContext() {
        if(context == null) throw new RuntimeException("Mockup precisa ser iniciado, chamar o metodo 'setContext'");
        return context;
    }

    public Mockup setContext(Context context) {
        this.context = context;
        return this;
    }

    public Mockup factoryManager(FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
        return this;
    }

    public Mockup debug(){
        debug = true;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public FactoryManager getFactoryManager() {
        if(factoryManager == null) throw new RuntimeException("Mockup precisa ser iniciado, chamar o metodo 'factoryManager'");
        return factoryManager;
    }

    public Mockup activeMonitorTasks() {
        monitorTasks = true;
        return this;
    }

    public boolean isMonitorTasks() {
        return monitorTasks;
    }
}
