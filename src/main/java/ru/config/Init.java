package ru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.models.Role;
import ru.models.User;
import ru.service.RoleService;
import ru.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class Init implements CommandLineRunner {


    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public Init(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception {

        Set<Role> firstUserRole = new HashSet<>();
        Set<Role> secondUserRole = new HashSet<>();

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        firstUserRole.add(adminRole);
        secondUserRole.add(userRole);

        roleService.save(adminRole);
        roleService.save(userRole);


        User firstUser = new User(1L, "Alex", "123",
                "alex@alex.com", "123456", firstUserRole);
        User secondUser = new User(2L, "Konstantin", "456",
                "konst@gmail.com", "123456", secondUserRole);


        userService.save(firstUser);
        userService.save(secondUser);


    }
}

