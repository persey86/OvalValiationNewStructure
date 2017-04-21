package com.department.validation;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created on 19.04.17.
 */
public class CustomValidator {

    private Validator validator;
    public CustomValidator(){
        this.validator = new Validator();
    }

    public void validateEntity(Object entity, Map<String, String> valid) {
        List<ConstraintViolation> violations = validator.validate(entity);
        if (violations.size() > 0) {
            for (ConstraintViolation violation : violations) {
                OValContext context = violation.getContext();

                if (context instanceof FieldContext) {
                    Field field = ((FieldContext) context).getField();
                    String fieldName = field.getName();
                    String value = violation.getMessage();
                    valid.put(fieldName, value);
                }
            }
        }
    }
}