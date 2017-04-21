package com.department.models;

import com.department.validation.UniqueUserEmail;
import net.sf.oval.constraint.*;

import java.util.Date;

/**
 * Created on 02.04.2017.
 */
public class User {
    private Integer id;
    private Integer departmentId;

    @Size(min = 3, message = "Field must must have more than 3 symbols")
    @NotEmpty(message = "Field 'name' can't be empty, type something")
    private String name;

    @Size(min = 3, message = "Field must must have more than 3 symbols")
    @NotEmpty(message = "Field 'surname' can't be empty, type something")
    private String surname;

    @CheckWith(value = UniqueUserEmail.class, message = "This e-mail has used, put other e-mail")
    @Email(message = "Uncorrected format e-mail, try to put other")
    @NotEmpty(message = "Field e-mail can't be empty")
    private String email;
    private Date created;


    @NotNegative(message = "Use positive numbers only")
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
