package com.department.exceptions;

import java.util.Map;

/**
 * Created by on 08/04/2017.
 */
public class AppValidationException extends AppException {
    private Map<String, String> mapErr;

    public AppValidationException(String message, Map<String, String> mapErr) {
        super(message);
        this.mapErr = mapErr;
    }

    public Map<String, String> getMapErr() {
        return mapErr;
    }
}
