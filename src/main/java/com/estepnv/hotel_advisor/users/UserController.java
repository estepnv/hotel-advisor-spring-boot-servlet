package com.estepnv.hotel_advisor.users;

import com.estepnv.hotel_advisor.iam.CreateUserModel;
import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.iam.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserModelAssembler modelAssembler;

    @Autowired
    PagedResourcesAssembler<User> pagedResourcesAssembler;

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> show(@PathVariable UUID id) {
        var user = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("User", id.toString()));

        return ResponseEntity.ok(modelAssembler.toModel(user));
    }

    @PermitAll
    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserModel request) {
        var user = service.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelAssembler.toModel(user));
    }

    @GetMapping("/api/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<?>> listUsers(Pageable pageable) {
        var users = repository.findAll(pageable);
        var model = pagedResourcesAssembler.toModel(users, modelAssembler);
        return ResponseEntity.ok(model);
    }

    @PutMapping("/api/users/{user_id}/add_role/{role_name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RepresentationModel<?>> addRoleToUser(
            @PathVariable("user_id") UUID userId,
            @PathVariable("role_name") String roleName) {

        var user = service.addRoleToUser(userId, roleName);

        return ResponseEntity.status(201).body(modelAssembler.toModel(user));
    }

    @DeleteMapping("/api/users/{user_id}/remove_role/{role_name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RepresentationModel<?>> removeRoleFromUser(
            @PathVariable("user_id") UUID userId,
            @PathVariable("role_name") String roleName) {

        var user = service.removeRoleFromUser(userId, roleName);

        return ResponseEntity.status(200).body(modelAssembler.toModel(user));
    }
}
