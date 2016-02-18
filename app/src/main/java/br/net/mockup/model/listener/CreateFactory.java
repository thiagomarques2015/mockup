package br.net.mockup.model.listener;

/**
 * Interface de criação de objetos de fábricas
 * Created by Thiago on 05/06/2015.
 */
public interface CreateFactory<T> {
    T create(String name);
}
