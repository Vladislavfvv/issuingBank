package com.edme.issuingBank.services;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T, E> {

    public List<E> findAll();

    public Optional<E> findById(T t);

    public Optional<E> save(E dto);

    public Optional<E> update(T id, E dto);

    public boolean delete(T id);

    public boolean deleteAll();

    //public boolean dropTable();
    public void dropTable();

//    public boolean createTable();
    public void createTable();

    //public boolean initializeTable();
    public void initializeTable();
}

