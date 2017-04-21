package com.department.servlet.actions;

import com.department.servlet.actions.impl.*;
import com.department.servlet.actions.impl.department.*;
import com.department.servlet.actions.impl.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 02.04.2017.
 */
public class ActionFactory {
    private static Map<String, Action> actions = new HashMap<>();
    private static Action notFound = new NotFound();

              static {
                  actions.put("GET/", new ShowAllDepartmentsAction());
                  actions.put("GET/allUsers", new ShowAllUsersAction());

                  actions.put("POST/deleteDepartment", new DeleteDepartmentAction());
                  actions.put("POST/deleteUser", new DeleteUserAction());

                  actions.put("GET/createOrUpdateDepartment", new ShowCreateOrUpdateDepartmentForm());
                  actions.put("POST/createOrUpdateDepartment", new CreateOrUpdateDepartmentAction());

                  actions.put("GET/createOrUpdateUser", new ShowCreateOrUpdateUserForm());
                  actions.put("POST/createOrUpdateUser", new CreateOrUpdateUserAction());
              }

    public static Action getAction(HttpServletRequest request) {
        String uri = request.getMethod() + request.getRequestURI();
        if (actions.containsKey(uri)) {
            return actions.get(uri);
        } else {
            return notFound;
        }
    }
}