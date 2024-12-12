package ru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.repositories.UserRepository;
import ru.exception.NoSuchUserException;
import ru.models.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user, Set<Long> roleIds) {
        if(roleIds != null) {
            user.setRoles(roleIds.stream()
                    .map(roleService::findById)
                    .collect(Collectors.toSet()));
        }
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws NoSuchUserException {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("couldn't find user with id " + id));
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
    @Override
    public void update(User updatedUser) {
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id != null && id > 0) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public User findByUsername(String username) throws NoSuchUserException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new NoSuchUserException("couldn't find user with username " + username));
    }
}