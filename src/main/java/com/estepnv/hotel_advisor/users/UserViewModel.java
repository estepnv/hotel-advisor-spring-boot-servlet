package com.estepnv.hotel_advisor.users;

import com.estepnv.hotel_advisor.iam.User;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserViewModel extends RepresentationModel<UserViewModel> {
    UUID id;
    String email;
    boolean enabled;
    Set<String> roles;
    Instant createdAt;
    Instant updatedAt;

    public UserViewModel(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.roles = new HashSet<>(user.getRoles().stream().map(role -> role.getName()).toList());
        this.createdAt = user.getCreatedAt().toInstant();
        this.updatedAt = user.getUpdatedAt().toInstant();
    }
}
