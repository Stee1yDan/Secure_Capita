package com.example.securecapita.repository.impl;

import com.example.securecapita.exception.ApiException;
import com.example.securecapita.model.Role;
import com.example.securecapita.model.User;
import com.example.securecapita.repository.RoleRepository;
import com.example.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.example.securecapita.enumeration.RoleType.ROLE_USER;
import static com.example.securecapita.exception.VerificationType.ACCOUNT;
import static com.example.securecapita.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl<T extends User> implements UserRepository<T>
{

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User data)
    {
        if (getEmailCount(data.getEmail().trim().toLowerCase()) > 1)
            throw new ApiException("Email already exist");
        try
        {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = getSqlParameterSource(data);
            jdbc.update(INSERT_USER_QUERY, parameterSource, holder);
            data.setId(Objects.requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(data.getId(), ROLE_USER.name());

            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", data.getId(), "url", verificationUrl));

//            emailService.sendVerificationUrl(data.getFirstName(), data.getEmail(), verificationUrl, ACCOUNT);
            data.setEnabled(false);
            data.setIsLocked(false);
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApiException("Unexpected error occurred:");
        }
    }


    @Override
    public Collection<T> list(int page, int pageSize)
    {
        return null;
    }

    @Override
    public T get(Long id)
    {
        return null;
    }

    @Override
    public T update(T data)
    {
        return null;
    }

    @Override
    public Boolean delete(Long id)
    {
        return null;
    }

    private int getEmailCount(String email)
    {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User data)
    {
        return new MapSqlParameterSource()
                .addValue("firstname", data.getFirstName())
                .addValue("lastname", data.getLastName())
                .addValue("email", data.getEmail())
                .addValue("password", encoder.encode(data.getPassword()));

    }

    private String getVerificationUrl(String key, String type)
    {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }
}
