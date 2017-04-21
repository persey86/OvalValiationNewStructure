package com.department.validation;

import com.department.exceptions.AppException;
import com.department.models.User;
import com.department.repository.UserRepository;
import com.department.repository.impl.UserRepositoryImpl;
import net.sf.oval.constraint.CheckWithCheck;


/**
 * Created on 20.04.17.
 */


public class UniqueUserEmail implements CheckWithCheck.SimpleCheck {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public boolean isSatisfied(Object validatedObject, Object value) {

        User user = (User) validatedObject;
        Integer id = user.getId();
        String email = user.getEmail();

        try{
            if (id == null) {
                if (userRepository.isAnyUsersHasThisName(email)) {
                    return false;
                }
            } else {
                if (userRepository.isAnotherUserHasThisEmail(id, email)) {
                    return false;
                }
            }
        }catch (AppException e){
            e.getMessage();
        }
        return true;
    }
}