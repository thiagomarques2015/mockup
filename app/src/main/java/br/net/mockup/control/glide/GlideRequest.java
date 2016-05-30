package br.net.mockup.control.glide;

import android.net.Uri;

import java.io.File;

import br.net.mockup.Mockup;

import static br.net.mockup.control.util.Printlog.debug;
import static br.net.mockup.control.util.Printlog.erro;

/**
 * Construtor de requisicao da imagem referente ao tipo da imagem (bytes, bitmap, etc.)
 * Created by Thiago on 28/07/2015.
 */
class GlideRequest {

    public static final String DIRETORIO = "Mockup";

    public static final int BITMAP = 1;
    public static final int BYTES = 2;

    public static Uri toURI(GlideImage glide) {
        if(glide.getUri() == null && glide.getUrl() == null && glide.getResource() == 0) throw new IllegalArgumentException("O load nao foi iniciado em GlideImage");
        if(glide.getUri()== null && (glide.getUrl() == null || glide.getUrl().isEmpty()) && glide.getPlaceHolder() == 0) throw new IllegalArgumentException("Falta o placeholder, pois a URL esta vazia");

        if (glide.getUri()== null) {
            if (glide.getUrl() != null) {
                if (glide.getUrl().isEmpty()) {
                    glide.setUri(Uri.parse(String.format("android.resource://%s/%s", glide.getContext().getPackageName(), glide.getPlaceHolder())));
                } else if (glide.getUrl().matches("^http.*")) { // Foto do servidor
                    glide.setUri(Uri.parse(glide.getUrl()));
                } else if (glide.getUrl().matches(".*\\/" + DIRETORIO + "\\/.*")) { // Foto carregada do aparelho
                    glide.setUri(getUriStorage(glide.getUrl()));
                    if(glide.getUri() == null )
                        erro("=> Arquivo de imagem nao encontrado!");
                } else { // Nao indentificado
                    Uri uri = null;
                    try {
                        uri = Uri.parse(glide.getUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    glide.setUri(uri);
                }
            } else { // Se for um recurso interno do Android ou do Projeto
                glide.setUri(Uri.parse(String.format("android.resource://%s/%s", glide.getContext().getPackageName(), glide.getResource())));
            }
        }

        if(glide.getUri()!= null){
            if(Mockup.getInstance().isDebug())
                debug(String.format("Imagem recebida url(%s)", glide.getUri().toString()));
        }
        else
            throw new IllegalArgumentException("A URI nao pode ser nula");

        return glide.getUri();
    }

    private static Uri getUriStorage(String url) {
        String dir = url.substring(0, url.lastIndexOf("/"));
        dir = dir.replaceAll("^file://", "");
        if(Mockup.getInstance().isDebug())
        debug("=> {DIR} " + dir);
        File file = new File(dir, url.substring(url.lastIndexOf("/")));
        if(!file.exists()) return null;
        return Uri.fromFile(file);
    }
}
