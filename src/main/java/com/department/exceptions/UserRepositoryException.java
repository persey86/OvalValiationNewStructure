package com.department.exceptions;

/**
 * Created on 03.04.2017.
 */
public class UserRepositoryException extends AppException {
        public UserRepositoryException(String message, Throwable cause){
        super(message, cause);
    }
}