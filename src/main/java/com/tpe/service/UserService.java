package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());

        // password hash'lenecek
        String password = userRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        // role setlenecek - default: admin (normalde admin olmaz default)
        Role role = roleService.getRoleType(UserRole.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRole(roles);

        userRepository.save(user);
    }
}
