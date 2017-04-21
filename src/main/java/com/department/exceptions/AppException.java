package com.department.exceptions;

/**
 * Created on 15.04.2017.
 */
public class AppException extends Exception {

    public AppException (String message){
        super(message);
    }
    public AppException (String message, Throwable cause){
        super(message, cause);
    }

}
