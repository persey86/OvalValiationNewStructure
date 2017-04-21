package com.department.services;

import java.util.List;

/**
 * Created on 15.04.2017.
 */
public interface GeneralService<T, E, EX extends Exception>{

    // get list of all objects of type "T"
    List<T> findAllEntities() throws EX;

    //get one object of type"T" by ID
    T findOneEntity(E id) throws EX;

    //save object entity by type "T"
    T saveEntityWithValidation(T entity) throws EX;

    //delete entity by ID(has type "E")
    void deleteEntityWithValidation(E id) throws EX;
}
