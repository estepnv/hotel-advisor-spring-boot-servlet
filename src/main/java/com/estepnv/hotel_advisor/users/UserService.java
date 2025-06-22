package com.estepnv.hotel_advisor.users;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.iam.RegisterRequest;
import com.estepnv.hotel_advisor.iam.RoleRepository;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.iam.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;



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

    public User addRoleToUser(UUID userId, String roleName) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User", userId.toString()));

        if (user.getRoles().stream().noneMatch(r -> r.getName().equals(roleName))) {
            var role = roleRepository.findRoleByName(roleName)
                    .orElseThrow(() -> new RecordNotFoundException("Role", roleName));

            user.addRole(role);
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    public User removeRoleFromUser(UUID userId, String roleName) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User", userId.toString()));

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals(roleName))) {
            var role = roleRepository.findRoleByName(roleName)
                    .orElseThrow(() -> new RecordNotFoundException("Role", roleName));

            user.removeRole(role);
            return userRepository.save(user);
        } else {
            return user;
        }
    }

}
