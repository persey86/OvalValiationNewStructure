package com.department.servlet.actions.impl.user;

import com.department.exceptions.AppException;
import com.department.repository.impl.UserRepositoryImpl;
import com.department.services.DepartmentService;
import com.department.services.UserService;
import com.department.services.impl.DepartmentServiceImpl;
import com.department.services.impl.UserServiceImpl;
import com.department.servlet.actions.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 06.04.17.
 */
public class DeleteUserAction implements Action {

    private UserService userService;
    private DepartmentService departmentService;

    public DeleteUserAction(){
        this.userService = new UserServiceImpl();
        this.departmentService = new DepartmentServiceImpl();
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {

        UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();
        String idDeletingUser = request.getParameter("userId");
        Integer intId = Integer.parseInt(idDeletingUser);
        this.userService.deleteEntityWithValidation(intId);
        userRepositoryImpl.delete(intId);

        response.sendRedirect("/");
    }
}

