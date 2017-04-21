package com.department.servlet.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 02.04.2017.
 */
public interface Action {
    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
