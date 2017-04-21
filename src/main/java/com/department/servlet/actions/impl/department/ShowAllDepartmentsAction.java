package com.department.servlet.actions.impl.department;

import com.department.exceptions.AppException;
import com.department.models.Department;
import com.department.services.DepartmentService;
import com.department.services.impl.DepartmentServiceImpl;
import com.department.servlet.actions.Action;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created on 02.04.2017.
 */
public class ShowAllDepartmentsAction implements Action {

    private DepartmentService departmentService;

    public ShowAllDepartmentsAction(){
        this.departmentService = new DepartmentServiceImpl();
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws AppException, ServletException, IOException {

        List<Department> allDepartments = departmentService.findAllEntities();

        request.setAttribute("allDepartments", allDepartments);

        request.getRequestDispatcher("/WEB-INF/" + "index" + ".jsp").forward(request,response);

    }
}
