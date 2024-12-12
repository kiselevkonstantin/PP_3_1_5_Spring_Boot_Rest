package ru.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.exception.NoSuchRoleException;
import ru.exception.NoSuchUserException;
import ru.models.Role;
import ru.models.User;
import ru.service.RoleService;
import ru.service.UserService;

import java.util.List;
import java.util.stream.Stream;

@Component
public class Init implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Init(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Role createRoleIfNotFound(String roleStr) {
        try {
            Role role = roleService.findByName(roleStr);
            return role;
        } catch (NoSuchRoleException e) {
            Role newRole = new Role();
            newRole.setName(roleStr);
            roleService.save(newRole);
            return newRole;
        }
    }

    @Transactional
    public User createUserIfNotFound(String userStr, List<Role> roles) {
        try {
            User user = userService.findByUsername(userStr);
            return user;
        } catch (NoSuchUserException e) {
            User newUser = new User(userStr);
            newUser.setPassword(passwordEncoder.encode(userStr));
            if (userStr.equals("admin")) {
                newUser.addRole(roles.get(0));
                newUser.addRole(roles.get(1));
            } else if (userStr.equals("user")) {
                newUser.addRole(roles.get(1));
            }
            return newUser;
        }
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        List<Role> roles = Stream.of("ROLE_ADMIN", "ROLE_USER").map(this::createRoleIfNotFound).toList();
        List<User> users = Stream.of("admin", "user").map(userStr -> createUserIfNotFound(userStr, roles)).toList();

        userService.saveAll(users);

        alreadySetup = true;
    }

}
