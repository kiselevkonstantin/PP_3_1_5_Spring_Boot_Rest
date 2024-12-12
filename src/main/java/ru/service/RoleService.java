package ru.service;

import ru.models.Role;

import ru.exception.NoSuchRoleException;

import java.util.List;

public interface RoleService {

    Role findByName(String name) throws NoSuchRoleException;

    Role save(Role role);

    List<Role> findAll();

    Role findById(Long id);

    void delete(Long id);

}
