package ru.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.converters.UserConverter;
import ru.dto.UserDTO;
import ru.models.User;
import ru.service.UserService;

import java.security.Principal;


@RestController
@RequestMapping("api/v1/user")
public class UserRestController {


    private final UserService userService;
    private final UserConverter userConverter;

    public UserRestController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping()
    public UserDTO userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        System.out.println(user);
        UserDTO dto = userConverter.convertToDTO(user);
        System.out.println(dto);

        return dto;
    }
}
