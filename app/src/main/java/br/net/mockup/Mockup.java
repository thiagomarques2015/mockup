package br.net.mockup;

import android.content.Context;

import br.net.mockup.control.factory.MockupFactoryManager;

/**
 * Created by Thiago on 10/11/2015.
 */
public class Mockup {
    private static Mockup ourInstance = new Mockup();

    private MockupFactoryManager mockupFactoryManager;
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

    public Mockup factoryManager(MockupFactoryManager mockupFactoryManager) {
        this.mockupFactoryManager = mockupFactoryManager;
        return this;
    }

    public Mockup debug(){
        debug = true;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public MockupFactoryManager getMockupFactoryManager() {
        if(mockupFactoryManager == null) throw new RuntimeException("Mockup precisa ser iniciado, chamar o metodo 'mockupFactoryManager'");
        return mockupFactoryManager;
    }

    public Mockup activeMonitorTasks() {
        monitorTasks = true;
        return this;
    }

    public boolean isMonitorTasks() {
        return monitorTasks;
    }
}
