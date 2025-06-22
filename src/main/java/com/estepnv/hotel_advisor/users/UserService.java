package com.estepnv.hotel_advisor.users;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.iam.RegisterRequest;
import com.estepnv.hotel_advisor.iam.RoleRepository;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.iam.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User registerUser(RegisterRequest request) {
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setEnabled(true);
        var userRole = roleRepository.findRoleByName("USER").orElseThrow(() -> new RecordNotFoundException("Role", "USER"));
        newUser.getRoles().add(userRole);
        newUser = userRepository.save(newUser);

        return newUser;
    }

}
