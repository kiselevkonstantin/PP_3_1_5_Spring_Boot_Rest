package ru.service;

import ru.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    void addUser(User user, Set<Long> roleIds);

    User findById(Long id);

    void save(User user);

    void saveAll(List<User> users);

    void update(User updatedUser);

    void delete(Long id);

    User findByUsername(String username);
}
