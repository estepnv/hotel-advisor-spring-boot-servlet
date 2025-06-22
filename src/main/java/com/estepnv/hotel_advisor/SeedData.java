package com.estepnv.hotel_advisor;

import com.estepnv.hotel_advisor.iam.Role;
import com.estepnv.hotel_advisor.iam.RoleRepository;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.iam.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void seedRoles() {
        roleRepository.findRoleByName("ADMIN")
                .orElseGet(() -> {
                    var newRole = new Role();
                    newRole.setName("ADMIN");
                    return roleRepository.save(newRole);
                });

        roleRepository.findRoleByName("USER")
                .orElseGet(() -> {
                    var newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });
    }

    @Transactional
    public void seedAdminUser() {
        var maybeUser = userRepository.findByEmail("admin@hoteladvisor.com");
        maybeUser.orElseGet(() -> {
            var admin = new User();
            admin.setEmail("admin@hoteladvisor.com");
            admin.setPassword(passwordEncoder.encode("Secret123!"));

            var adminRole = roleRepository.findRoleByName("ADMIN").orElseThrow(() -> new RuntimeException());
            var userRole = roleRepository.findRoleByName("USER").orElseThrow(() -> new RuntimeException());;

            admin.addRole(userRole);
            admin.addRole(adminRole);
            admin.setEnabled(true);

            return userRepository.save(admin);
        });
    }

    @Transactional
    public void makeSureAllUsersAssignedToUserRole() {
        var pageable = Pageable.ofSize(100);
        Page<User> page;
        var userRole = roleRepository.findRoleByName("USER").orElseThrow(() -> new RuntimeException());;

        do {
            page = userRepository.findAll(pageable);
            var usersWithoutRole = page.stream()
                    .filter(user -> user.getRoles().stream().noneMatch(role -> role.getName() == "USER"));

            var updatedUsers = usersWithoutRole.peek(user -> user.addRole(userRole)).toList();

            userRepository.saveAll(updatedUsers);
            pageable = page.nextPageable();
        } while (page.hasNext());
    }

    @Override
    public void run(String ...args) {
        seedRoles();
        seedAdminUser();
        makeSureAllUsersAssignedToUserRole();

    }
}
