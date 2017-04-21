package com.department.services.impl;

import com.department.exceptions.AppException;
import com.department.exceptions.AppValidationException;
import com.department.exceptions.DepartmentRepositoryException;
import com.department.models.Department;
import com.department.repository.DepartmentRepository;
import com.department.repository.impl.DepartmentRepositoryImpl;
import com.department.services.DepartmentService;
import com.department.validation.CustomValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 15.04.2017.
 */
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    private CustomValidator customValidator = new CustomValidator();

    @Override
    public List<Department> findAllEntities() throws AppException {
        return departmentRepository.findAll();
    }

    @Override
    public Department findOneEntity(Integer id) throws AppException {
        return departmentRepository.findOne(id);
    }

    @Override
    public Department saveEntityWithValidation(Department entity) throws AppException {
        validate(entity);
        return departmentRepository.save(entity);
    }

    @Override
    public void deleteEntityWithValidation(Integer id) throws AppException {
        departmentRepository.delete(id);
    }

    private void validate(Object entity) throws AppValidationException, DepartmentRepositoryException {

        Map<String, String> valid = new HashMap<>();

        this.customValidator.validateEntity(entity, valid);
        if (valid.size() > 0) {
            throw new AppValidationException("element not valid for department",valid);
        }
    }


}
