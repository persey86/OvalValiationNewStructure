package com.department.repository;

import java.util.List;

/**
 * Created on 15.04.2017.
 */
public interface GeneralRepository <T, E, EX extends Exception>{

    // get list of all objects of type "T"
    List<T> findAll() throws EX;

    //get one object of type"T" by ID
    T findOne(E id) throws EX;

    //save object entity by type "T"
    T save(T entity) throws EX;

    //delete entity by ID(has type "E")
    void delete(E id) throws EX;

}
