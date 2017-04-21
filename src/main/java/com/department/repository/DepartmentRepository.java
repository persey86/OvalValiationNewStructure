package com.department.repository;

import com.department.exceptions.DepartmentRepositoryException;
import com.department.models.Department;

/**
 * Created on 15.04.2017.
 */
public interface DepartmentRepository extends GeneralRepository <Department, Integer, DepartmentRepositoryException>{

    Boolean isAnyDepartmentHasThisName(String newDepartmentName) throws DepartmentRepositoryException;
    Boolean isAnotherDepartmentHasThisName(Integer departmentId, String newDepartmentName) throws DepartmentRepositoryException;
}
