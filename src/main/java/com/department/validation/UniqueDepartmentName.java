package com.department.validation;

import com.department.exceptions.AppException;
import com.department.models.Department;
import com.department.repository.DepartmentRepository;
import com.department.repository.impl.DepartmentRepositoryImpl;
import net.sf.oval.constraint.CheckWithCheck;

/**
 * Created on 20.04.17.
 */
public class UniqueDepartmentName implements CheckWithCheck.SimpleCheck {

    private DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

    @Override
    public boolean isSatisfied(Object validatedObject, Object value) {

        Department department = (Department) validatedObject;
        Integer id = department.getId();
        String name = department.getName();
        try {
            if (id == null) {
                if (departmentRepository.isAnyDepartmentHasThisName(name)) {
                    return false;
                }
            } else {
                if (departmentRepository.isAnotherDepartmentHasThisName(id, name)) {
                    return false;
                }
            }
        }catch (AppException e){
            e.getMessage();
        }
        return true;
    }
}