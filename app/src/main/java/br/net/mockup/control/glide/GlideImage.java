package br.net.mockup.control.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.signature.MediaStoreSignature;

import br.net.mockup.model.listener.PoolObject;
import br.net.mockup.model.pool.ObjectPool;

/**
 * Gerenciador de Imagens com a biblioteca Glide
 * https://github.com/bumptech/glide
 * Created by Thiago on 28/07/2015.
 */
public class GlideImage implements ReusableObject{

    private final CacheGlideManager cacheFactory; // Fabrica de objetos de cache
    private CircleTransform circleTransform;
    private Context context; // Contexto
    private String url; // URL da imagem
    private Uri uri; // Uri da imagem
    private Integer resource; // Imagem em forma de recurso
    private Integer placeHolder; // Recurso que substitui caso nao tenha imagem
    private BitmapRequestBuilder builder; // Requisicao construtora da imagem
    private ImageView imageView; // Lista de views de imagem
    private ImageDelegate.BytesListener bytesDelegate; // lista de callback de bytes de imagens
    private ImageDelegate.BitmapListener bitmapDelegate; // lista de callback de imagens
    private RequestManager requestManager; // Request Manager
    private ObjectPool factory; // Fabrica de objetos
    private PoolObject poolObject;

    public GlideImage() {
        cacheFactory = CacheGlideManager.getInstance();
    }

    @Override
    public void initialize() {
        url = null;
        uri = null;
        resource = 0;
        placeHolder = 0;
        builder = null;
        imageView = null;
        bytesDelegate = null;
        bitmapDelegate = null;
    }

    public GlideImage load(String url) {
        this.url = url;
        return this;
    }

    public GlideImage load(Uri uri) {
        this.uri = uri;
        return this;
    }

    public GlideImage load(int resource) {
        this.resource = resource;
        return this;
    }

    public GlideImage placeHolder(int placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public Uri parseToUri(){
        if(context == null) throw new IllegalArgumentException("O context nao pode ser nulo");
        if(requestManager == null) throw new IllegalArgumentException("O requestManager nao pode ser nulo");
        if(factory == null && poolObject == null) throw new IllegalArgumentException("O metodo factory precisa ser chamado");
        return GlideRequest.toURI(this);
    }

    public GlideImage build(int type){
        if(context == null) throw new IllegalArgumentException("O context nao pode ser nulo");
        if(requestManager == null) throw new IllegalArgumentException("O requestManager nao pode ser nulo");
        if(factory == null && poolObject == null) throw new IllegalArgumentException("O metodo factory precisa ser chamado");
        BitmapTypeRequest request = requestManager.load(GlideRequest.toURI(this)).asBitmap();

        // Configura a requisicao da imagem para RGB
        builder = request.format(DecodeFormat.PREFER_RGB_565);

        // Transforma em bytes a saida
        switch (type){
            case GlideRequest.BYTES :
                builder = request.toBytes(Bitmap.CompressFormat.JPEG, GlideBuilder.QUALITY);
                break;
        }
        return this;
    }

    public GlideImage resize(int width, int height){
        if(width == 0 && height == 0) throw new IllegalArgumentException("Width e Height nao podem ser 0");
        if(builder == null) throw new IllegalArgumentException("Falta iniciar build");
        builder = builder.override(width, height).centerCrop();
        return this;
    }

    public GlideImage into(ImageView imageView) {
        this.imageView = imageView;
        GlideBuilder.bitmapAndImageView(this);
        return this;
    }

    public GlideImage cache(long time){
        if(builder == null) throw new IllegalArgumentException("Falta iniciar o objeto de build");
        String sTimeStamp = time + "";
        // Recupera o objeto do cache caso ele ja tenha sido criado
        CacheGlide cache = cacheFactory.store(MediaStoreSignature.STRING_CHARSET_NAME, time, GlideBuilder.PORTRAIT).get(sTimeStamp);
        // Cria o cache da imagem
        builder = builder.signature(cache);
        return this;
    }

    public GlideImage circle(){
        if(builder == null) throw new IllegalArgumentException("Precisa ter uma requisicao para utilizar o crop em circulo");
        builder = builder.transform(circleTransform);
        return this;
    }

    public GlideImage toBitmap(){
        GlideBuilder.toBitmap(this);
        return this;
    }

    public GlideImage toBytes(int width, int height){
        GlideBuilder.toBytes(this, width, height);
        return this;
    }

    public GlideImage addDelegateImageBitmap(ImageDelegate.BitmapListener delegate) {
        bitmapDelegate = delegate;
        return this;
    }

    public GlideImage addDelegateImageBytes(ImageDelegate.BytesListener delegate) {
        bytesDelegate = delegate;
        return this;
    }

    public void setCircleTransform(CircleTransform circleTransform) {
        this.circleTransform = circleTransform;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setBuilder(BitmapRequestBuilder builder) {
        this.builder = builder;
    }

    public BitmapRequestBuilder getBuilder() {
        return builder;
    }

    public ImageDelegate.BitmapListener getBitmapDelegate() {
        return bitmapDelegate;
    }

    public ImageDelegate.BytesListener getBytesDelegate() {
        return bytesDelegate;
    }

    public Uri getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }

    public Integer getPlaceHolder() {
        return placeHolder;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Integer getResource() {
        return resource;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void free() {
        factory.freeObject(poolObject);
    }

    public GlideImage factory(PoolObject poolObject, ObjectPool factory) {
        this.poolObject = poolObject;
        this.factory = factory;
        return this;
    }
}
