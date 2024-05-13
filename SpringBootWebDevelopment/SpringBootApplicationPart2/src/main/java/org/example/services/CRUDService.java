package org.example.services;

import java.util.Collection;

public interface CRUDService<T> {
    T getById(Long id);

    Collection<T> getALl();

    T create(T item);

    T update(T item);

    String delete(Long id);
}
