package com.department.repository.impl;

import com.department.exceptions.DepartmentRepositoryException;
import com.department.models.Department;
import com.department.repository.DepartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.department.utils.SqlUtils.getConnection;

/**
 * Created on 02.04.2017.
 */
public class DepartmentRepositoryImpl implements DepartmentRepository{

    private static final String SELECT_FROM_DEPARTMENTS_IS_ANY_BUT_NOT_ME = "SELECT count(*) as total from departments  where departments.id <> ? and departments.name = ?";
    private static final String SELECT_FROM_DEPARTMENTS_IS_ANY = "SELECT count(*) as total from departments where departments.name = ?";
    private static final String SELECT_FROM_DEPARTMENTS = "SELECT * from departments d ";
    private static final String INSERT_INTO_DEPARTMENTS = "INSERT INTO departments (name, created) VALUES (?, ?)";
    private static final String UPDATE_DEPARTMENT = "UPDATE departments SET name = ?, created = ? WHERE id = ?";
    private static final String DELETE_DEPARTMENTS = "DELETE FROM departments WHERE id = ?";
    private static final String GET_DEPARTMENT_WITH_SELECTED_id = "SELECT * from departments where departments.id = ?";


    @Override
    public Department save(Department entity) throws DepartmentRepositoryException {

        //we should change variable "entity" to "department" inside the method only to avoid bad code in signature
        Department department = entity;
        Integer id = department.getId();

        if (id == null) {

            //Do save if entity doesn't have ID
            department = createDepartment(department.getName(), department.getCreated());
        }else {
            updateDepartment(department.getId(),department.getName(),department.getCreated());
        }

        //if create or update not done this method will throw Exception
        return department;
    }


    @Override
    public List<Department> findAll() throws DepartmentRepositoryException {

        List<Department> departments = new ArrayList<>();

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_DEPARTMENTS);

            while (resultSet.next()) {
                Department user = new Department();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setCreated(resultSet.getDate("created"));
                departments.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DepartmentRepositoryException("Error while getting departments",e);
        }
        return departments;
    }

    private Department createDepartment(String name, Date created) throws DepartmentRepositoryException {
        try (Connection connection = getConnection()) {
            Department department = null;

            PreparedStatement pStm = connection.prepareStatement(INSERT_INTO_DEPARTMENTS);
            pStm.setString(1, name);
            pStm.setDate(2, new java.sql.Date(created.getTime()));
            Integer isUpdated = pStm.executeUpdate();


            if (isUpdated > 0) {
                department = new Department();
                department.setName(name);
                department.setCreated(created);
            }

            return department;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DepartmentRepositoryException("Error while adding new department",e);
        }
    }

    @Override
    public void delete(Integer id) throws DepartmentRepositoryException {
        try (Connection connection = getConnection()) {
            PreparedStatement pStm = connection.prepareStatement(DELETE_DEPARTMENTS);
            pStm.setInt(1, id);
            pStm.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DepartmentRepositoryException("Error while deleted models form SQL ",e);
        }
    }

    @Override
    public Department findOne(Integer id) throws DepartmentRepositoryException {

        try (Connection connection = getConnection()) {
            Department department = null;

            PreparedStatement pStm = connection.prepareStatement(GET_DEPARTMENT_WITH_SELECTED_id);
            pStm.setInt(1, id);
            ResultSet resultSet = pStm.executeQuery();
            while (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setCreated(resultSet.getDate("created"));
            }
            return department;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DepartmentRepositoryException("Error while getting departments by ID",e);
        }
    }

    private void updateDepartment(Integer id, String name, Date created) throws DepartmentRepositoryException {
        try (Connection connection = getConnection()) {
            PreparedStatement pStm = connection.prepareStatement(UPDATE_DEPARTMENT);

            pStm.setString(1, name);
            pStm.setDate(2, new java.sql.Date(created.getTime()));
            pStm.setInt(3, id);

           pStm.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new DepartmentRepositoryException("Error while updating departments by ID",e);
        }
    }


    // we use this when create department to check name uniq
    @Override
    public Boolean isAnyDepartmentHasThisName(String newDepartmentName) throws DepartmentRepositoryException {

        // https://stackoverflow.com/questions/2763659/how-do-you-access-the-value-of-an-sql-count-query-in-a-java-program

        try (Connection connection = getConnection()) {
            Integer count = 0;

            PreparedStatement pStm = connection.prepareStatement(SELECT_FROM_DEPARTMENTS_IS_ANY);

            pStm.setString(1, newDepartmentName);
            ResultSet resultSet = pStm.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
            return count > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DepartmentRepositoryException("Error when check is any department has this name",e);
        }
    }

    // we use this when update department to check name uniq
    @Override
    public Boolean isAnotherDepartmentHasThisName(Integer departmentId, String newDepartmentName) throws DepartmentRepositoryException {
        // https://stackoverflow.com/questions/2763659/how-do-you-access-the-value-of-an-sql-count-query-in-a-java-program

        try (Connection connection = getConnection()) {
            Integer counter = 0;

            PreparedStatement pStm = connection.prepareStatement(SELECT_FROM_DEPARTMENTS_IS_ANY_BUT_NOT_ME);
            pStm.setInt(1, departmentId);
            pStm.setString(2, newDepartmentName);
            ResultSet resultSet = pStm.executeQuery();

            while (resultSet.next()) {
                counter = resultSet.getInt("total");
            }
            return counter > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DepartmentRepositoryException("Error while getting departments by ID",e);
        }
    }

}