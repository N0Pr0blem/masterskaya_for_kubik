package com.kubik.masterskaya.service.base;

import java.util.List;

public interface BaseService<T, I, D> {
    List<T> findAll();

    T findById(I i);

    void deleteById(I i);

    T save(T t);

    T update(I i, D d);
}
