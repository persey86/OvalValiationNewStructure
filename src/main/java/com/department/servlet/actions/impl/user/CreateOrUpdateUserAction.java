package com.department.servlet.actions.impl.user;

import com.department.exceptions.AppException;
import com.department.exceptions.AppValidationException;
import com.department.models.User;
import com.department.services.DepartmentService;
import com.department.services.UserService;
import com.department.services.impl.DepartmentServiceImpl;
import com.department.services.impl.UserServiceImpl;
import com.department.servlet.actions.Action;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created on 19.04.2017.
 */
public class CreateOrUpdateUserAction  implements Action {
    private UserService userService;
    private DepartmentService departmentService;

    public CreateOrUpdateUserAction(){
        this.userService = new UserServiceImpl();
        this.departmentService = new DepartmentServiceImpl();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws AppException, ServletException, IOException {

        User user = new User();
        String userIdString = request.getParameter("userId");
        Integer userId = null;
        if (!userIdString.equals("")) {
            userId = Integer.parseInt(userIdString);
        }

        String nameUser = request.getParameter("userName");
        String surnameUser = request.getParameter("userSurname");
        String emailUser = request.getParameter("userEmail");
        Integer ageUser = Integer.parseInt(request.getParameter("userAge"));
        Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));

        user.setId(userId);
        user.setName(nameUser);
        user.setSurname(surnameUser);
        user.setEmail(emailUser);
        user.setAge(ageUser);
        user.setCreated(new Date());
        user.setDepartmentId(departmentId);

        try {

            //  update user
            userService.saveEntityWithValidation(user);

            response.sendRedirect("/allUsers?id=" +  departmentId);

        }catch (AppValidationException e){
            Map<String,String> mapErr = e.getMapErr();
            request.setAttribute("mapErr", mapErr);

            request.setAttribute("allDepartments", departmentService.findAllEntities());
            request.setAttribute("user", user);

            request.getRequestDispatcher("/WEB-INF/" + "createOrUpdateUserForm" + ".jsp").forward(request,response);
        }
    }
}
