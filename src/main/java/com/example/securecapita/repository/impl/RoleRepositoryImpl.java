package com.example.securecapita.repository.impl;

import com.example.securecapita.exception.ApiException;
import com.example.securecapita.model.Role;
import com.example.securecapita.repository.RoleRepository;
import com.example.securecapita.util.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;


import static com.example.securecapita.enumeration.RoleType.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role>
{
    private static final String SELECT_ROLE_BY_NAME_QUERY = "";
    private static final String ADD_ROLE_TO_USER_QUERY = "";

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data)
    {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize)
    {
        return null;
    }

    @Override
    public Role get(Long id)
    {
        return null;
    }

    @Override
    public Role update(Role data)
    {
        return null;
    }

    @Override
    public Boolean delete(Long id)
    {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName)
    {
        log.info("Adding role {} to user with id: {}", roleName, userId);
        try
        {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName", roleName), new RoleRowMapper());
            jdbc.update(ADD_ROLE_TO_USER_QUERY, Map.of("userId", userId, "role", Objects.requireNonNull(role).getId()));

        }
        catch (EmptyResultDataAccessException e)
        {
            throw new ApiException("No such role exist: " + ROLE_USER.name());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApiException("Unexpected error occurred:");
        }

    }

    @Override
    public Role getRoleByUserId(Long id)
    {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email)
    {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, Role role)
    {

    }
}
