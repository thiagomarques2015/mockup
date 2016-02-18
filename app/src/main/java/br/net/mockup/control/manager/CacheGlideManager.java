package br.net.mockup.control.manager;

import br.net.mockup.Mockup;
import br.net.mockup.control.glide.CacheGlide;
import br.net.mockup.model.manager.ObjectManager;

/**
 * Created by Thiago on 31/07/2015.
 */
public class CacheGlideManager extends ObjectManager<CacheGlide> {
    private static CacheGlideManager instance = new CacheGlideManager();
    // Configuracoes do cache
    private String mimeType; // Codificacao
    private long dateModified; // Data de modificacao
    private int orientation; // Orientacao: 1 - Portrait

    protected CacheGlideManager() {
        super(Mockup.getInstance().getContext(), Mockup.getInstance().getFactoryManager(), "");
    }

    public static CacheGlideManager getInstance() {
        return instance;
    }

    @Override
    public CacheGlide create() {
        if(mimeType == null || dateModified == 0 || orientation == 0) throw new IllegalArgumentException("Falta iniciar configuracoes em store");
        CacheGlide cache = new CacheGlide(mimeType, dateModified, orientation);
        // Limpa as configuracoes antiga
        mimeType = null;
        dateModified = 0;
        orientation = 0;
        return cache;
    }

    public CacheGlideManager store(String mimeType, long dateModified, int orientation){
        this.mimeType = mimeType;
        this.dateModified = dateModified;
        this.orientation = orientation;
        return this;
    }

    @Override
    protected void onChangedObject(CacheGlide obj) {

    }

    @Override
    public String toString() {
        return CacheGlide.class.getSimpleName();
    }
}
