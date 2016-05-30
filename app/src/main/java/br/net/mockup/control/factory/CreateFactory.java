package br.net.mockup.control.factory;

/**
 * Interface de criação de objetos de fábricas
 * Created by Thiago on 05/06/2015.
 */
interface CreateFactory<T> {
    T create(String name);
}
