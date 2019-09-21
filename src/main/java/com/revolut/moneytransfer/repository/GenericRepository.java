package com.revolut.moneytransfer.repository;

import java.util.List;

public interface GenericRepository<E> {

    E add(E e);

    E update(E e);

    boolean delete(Integer id);

    E findById(Integer id);

    List<E> getAll();
}
