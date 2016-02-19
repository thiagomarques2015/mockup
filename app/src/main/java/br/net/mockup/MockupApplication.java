package br.net.mockup;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Handler;

import br.net.mockup.model.manager.FactoryManager;

import static br.net.mockup.control.util.Printlog.info;

/**
 * Created by Thiago on 28/10/2015.
 */
public class MockupApplication extends android.app.Application {

    private static MockupApplication instance;
    private Handler handler = new Handler();
    private Mockup mockup;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // Inicia o mockup
        mockup = Mockup.getInstance().setContext(this);
    }

    public void factoryMain(FactoryManager factoryManager){
        // Adiciona ao mockup o gerenciador de fabricas
        mockup.factoryManager(factoryManager);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        switch (level){
            // app in background
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN :
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW :
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE :
                gc();
                break;
        }
    }

    void gc(){
        info("Limpando a memoria, iniciando o GC");
        handler.postDelayed(cleanerTask, 1000);
    }

    public static Context getContext(){
        return instance;
    }

    private Runnable cleanerTask = new Runnable() {
        @Override
        public void run() {
            System.gc();
            System.runFinalization();
        }
    };
}
