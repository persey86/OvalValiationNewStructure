package com.department.servlet.actions.impl.user;

import com.department.repository.DepartmentRepository;
import com.department.repository.UserRepository;
import com.department.repository.impl.DepartmentRepositoryImpl;
import com.department.repository.impl.UserRepositoryImpl;
import com.department.servlet.actions.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 19.04.2017.
 */
public class ShowCreateOrUpdateUserForm implements Action {

    private DepartmentRepository departmentRepositoryImpl;
    private UserRepository userRepositoryImpl;

    public ShowCreateOrUpdateUserForm() {
        this.departmentRepositoryImpl = new DepartmentRepositoryImpl();
        this.userRepositoryImpl = new UserRepositoryImpl();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("allDepartments", departmentRepositoryImpl.findAll());

        String userIdString = request.getParameter("userId");

        if (userIdString != null) {
            Integer userId = Integer.parseInt(userIdString);
            request.setAttribute("user", userRepositoryImpl.findOne(userId));
        }
        request.getRequestDispatcher("/WEB-INF/" + "createOrUpdateUserForm" + ".jsp").forward(request, response);
    }
}
