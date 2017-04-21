package com.department.repository;

import com.department.exceptions.UserRepositoryException;
import com.department.models.User;

import java.util.List;

/**
 * Created on 15.04.2017.
 */
public interface UserRepository extends GeneralRepository<User, Integer, UserRepositoryException>{

    Boolean isAnyUsersHasThisName(String email) throws UserRepositoryException;

    List<User> getUsersWhereDepartmentId(Integer departmentId) throws UserRepositoryException;

    Boolean isAnotherUserHasThisEmail(Integer userId, String email) throws UserRepositoryException;
}
