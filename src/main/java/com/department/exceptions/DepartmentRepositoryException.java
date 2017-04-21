package com.department.exceptions;

/**
 * Created on 02.04.2017.
 */
public class DepartmentRepositoryException extends AppException {
        public DepartmentRepositoryException (String message, Throwable cause){
        super(message, cause);
    }
}
