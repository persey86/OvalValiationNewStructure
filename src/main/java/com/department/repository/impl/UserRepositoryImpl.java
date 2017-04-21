package com.department.repository.impl;

import com.department.exceptions.UserRepositoryException;
import com.department.models.User;
import com.department.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.department.utils.SqlUtils.getConnection;

/**
 * Created  on 02.04.2017.
 */
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_FROM_USERS_IS_ANY_BUT_NOT_ME = "SELECT count(*) as total from users  where users.id <> ? and users.email = ?";
    private static final String SELECT_FROM_USERS = "SELECT * from users";
    private static final String SELECT_FROM_USERS_IS_ANY = "SELECT count(*) as total from users where users.email = ?";
    private static final String SELECT_FROM_USERS_WHERE_DEP_id = "SELECT * from users u where u.department_id = ?";
    private static final String INSERT_INTO_USERS = "INSERT INTO users (name,surname,email,created,age,department_id) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, surname = ?, email = ?, created = ?, age = ?, department_id =?  WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id= ?";
    private static final String SELECT_FROM_USERS_BY_USER_ID = "SELECT * from users where users.id = ?";

    @Override
    public List<User> findAll() throws UserRepositoryException {

        try (Connection connection = getConnection()) {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_USERS);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setCreated(resultSet.getDate("created"));
                user.setAge(resultSet.getInt("age"));
                user.setDepartmentId(resultSet.getInt("department_id"));
                users.add(user);
            }
            return users;
        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while getting list of users", e);
        }
    }


    @Override
    public User save(User entity) throws UserRepositoryException {
        User user = entity;
        Integer id = user.getId();

        if (id == null){
            user = createUser(user.getName(),user.getSurname(),user.getEmail(),user.getCreated(),user.getAge(),user.getDepartmentId());
        } else {
            updateUser(user.getId(),user.getName(),user.getSurname(),user.getEmail(),user.getCreated(),user.getAge(),user.getDepartmentId());
        }
        return user;
    }


    @Override
    public List<User> getUsersWhereDepartmentId(Integer departmentId) throws UserRepositoryException {

        try (Connection connection = getConnection()) {
            List<User> users = new ArrayList<>();

            PreparedStatement pStm = connection.prepareStatement(SELECT_FROM_USERS_WHERE_DEP_id);
            pStm.setInt(1, departmentId);
            ResultSet rs = pStm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setCreated(rs.getDate("created"));
                user.setDepartmentId(rs.getInt("department_id"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
            return users;
        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while getting users from selected department by Id", e);
        }
    }


    @Override
    public void delete(Integer id) throws UserRepositoryException {

        try (Connection connection = getConnection()) {
            PreparedStatement pStm = connection.prepareStatement(DELETE_USER);
            pStm.setInt(1, id);
            pStm.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while deleting user", e);
        }
    }


    private User createUser(String name, String surname, String email, Date created, Integer age, Integer departmentId) throws UserRepositoryException {

        try(Connection connection = getConnection()) {

            PreparedStatement pStm = connection.prepareStatement(INSERT_INTO_USERS);
            pStm.setString(1, name);
            pStm.setString(2, surname);
            pStm.setString(3, email);
            pStm.setDate(4, new java.sql.Date(created.getTime()));
            pStm.setInt(5, age);
            pStm.setInt(6, departmentId);

            int isUpdate = pStm.executeUpdate();

            User user = null;
            if (isUpdate > 0) {
                user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setEmail(email);
                user.setCreated(created);
                user.setAge(age);
                user.setDepartmentId(departmentId);
            }
            return user;
        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while adding new User", e);
        }
    }


    private void updateUser(Integer id, String name, String surname, String email, Date created, Integer age, Integer departmentId) throws UserRepositoryException {

        try(Connection connection = getConnection()) {

            PreparedStatement pStm = connection.prepareStatement(UPDATE_USER);
            pStm.setString(1, name);
            pStm.setString(2, surname);
            pStm.setString(3, email);
            pStm.setDate(4, new java.sql.Date(created.getTime()));
            pStm.setInt(5, age);
            pStm.setInt(6, departmentId);
            pStm.setInt(7, id);

            pStm.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while updating User", e);
        }
    }

    // we use this when create user to check name unique
    @Override
    public Boolean isAnyUsersHasThisName(String email) throws UserRepositoryException {

        // https://stackoverflow.com/questions/2763659/how-do-you-access-the-value-of-an-sql-count-query-in-a-java-program

        try (Connection connection = getConnection()) {
            Integer count = 0;

            PreparedStatement pStm = connection.prepareStatement(SELECT_FROM_USERS_IS_ANY);
            pStm.setString(1, email);
            ResultSet resultSet = pStm.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
            if (count > 0) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserRepositoryException("Error when check is any user has this name", e);
        }
        return false;
    }

@Override
    public User findOne(Integer id) throws UserRepositoryException {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_BY_USER_ID);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = new User();
            while (resultSet.next()) {

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setCreated(resultSet.getDate("created"));
                user.setAge(resultSet.getInt("age"));
                user.setDepartmentId(resultSet.getInt("department_id"));

            }
            return user;
        } catch (ClassNotFoundException | SQLException e) {
            throw new UserRepositoryException("Error while getting list of users", e);
        }
    }

    // we use this when update user to check name uniq
    @Override
    public Boolean isAnotherUserHasThisEmail(Integer userId, String email) throws UserRepositoryException {

        // https://stackoverflow.com/questions/2763659/how-do-you-access-the-value-of-an-sql-count-query-in-a-java-program

        try (Connection connection = getConnection()) {
            Integer count = 0;

            PreparedStatement pStm = connection.prepareStatement(SELECT_FROM_USERS_IS_ANY_BUT_NOT_ME);
            pStm.setInt(1, userId);
            pStm.setString(2, email);
            ResultSet resultSet = pStm.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
            return count > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserRepositoryException("Error while checking is another user has this name", e);
        }
    }

}