package com.department.models;

import com.department.validation.UniqueDepartmentName;
import net.sf.oval.constraint.*;

import java.util.Date;

/**
 * Created  on 02.04.2017.
 */
public class Department {
    private Integer id;

    @CheckWith(value = UniqueDepartmentName.class,message = "This name of department has used")
    @Length(min = 3, message = "Put more than 3 symbols")
    @NotEmpty(message = "Field can't be empty")
    protected String name;
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
