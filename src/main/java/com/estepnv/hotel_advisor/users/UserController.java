package com.estepnv.hotel_advisor.users;

import com.estepnv.hotel_advisor.iam.RegisterRequest;
import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.iam.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest request) {
        var user = service.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelAssembler.toModel(user));
    }

    @GetMapping("/api/users")
    public ResponseEntity<PagedModel<?>> listUsers(Pageable pageable) {
        var users = repository.findAll(pageable);
        var model = pagedResourcesAssembler.toModel(users, modelAssembler);
        return ResponseEntity.ok(model);
    }
}
