package com.department.services.impl;

import com.department.exceptions.AppException;
import com.department.exceptions.AppValidationException;
import com.department.exceptions.UserRepositoryException;
import com.department.models.User;
import com.department.repository.UserRepository;
import com.department.repository.impl.UserRepositoryImpl;
import com.department.services.UserService;
import com.department.validation.CustomValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 16.04.2017.
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private CustomValidator customValidator = new CustomValidator();

    @Override
    public List<User> findAllEntities() throws AppException {
        return userRepository.findAll();
    }

    @Override
    public User findOneEntity(Integer id) throws AppException {
        return userRepository.findOne(id);
    }

    @Override
    public User saveEntityWithValidation(User entity) throws AppException {
        validate(entity);
        return userRepository.save(entity);
    }

    @Override
    public void deleteEntityWithValidation(Integer id) throws AppException {
        userRepository.delete(id);
    }

    private void validate(Object entity) throws AppValidationException, UserRepositoryException {

        Map<String, String> valid = new HashMap<>();

        this.customValidator.validateEntity(entity, valid);
        if (valid.size() > 0) {
            throw new AppValidationException("element not valid for user",valid);
        }
    }
}
