package com.department.servlet.actions.impl.user;

import com.department.exceptions.UserRepositoryException;
import com.department.models.User;
import com.department.repository.impl.UserRepositoryImpl;
import com.department.services.DepartmentService;
import com.department.services.UserService;
import com.department.services.impl.DepartmentServiceImpl;
import com.department.services.impl.UserServiceImpl;
import com.department.servlet.actions.Action;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created on 06.04.17.
 */
public class ShowAllUsersAction implements Action {

    private UserService userService;
    private DepartmentService departmentService;

    public ShowAllUsersAction() {
        this.userService = new UserServiceImpl();
        this.departmentService = new DepartmentServiceImpl();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UserRepositoryException, ServletException, IOException {

        Integer departmentId = Integer.parseInt(request.getParameter("id"));
        UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();
        List<User> allUsers = userRepositoryImpl.getUsersWhereDepartmentId(departmentId);

        request.setAttribute("allUsers", allUsers);

        request.getRequestDispatcher("/WEB-INF/" + "allUsers" + ".jsp").forward(request, response);

    }
}
