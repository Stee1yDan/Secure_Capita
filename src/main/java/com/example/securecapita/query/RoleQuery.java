package com.example.securecapita.query;

public class RoleQuery
{
    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM roles WHERE name = :name";
    public static final String ADD_ROLE_TO_USER_QUERY = "INSERT INTO user_role (user_id, role_id) " +
            "VALUES (:userId, :roleId)";
}
