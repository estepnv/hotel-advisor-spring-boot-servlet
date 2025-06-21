package com.estepnv.hotel_advisor.iam;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
class UserRoleKey implements Serializable {

    @Column(name="user_id")
    UUID userId;

    @Column(name ="role_id")
    UUID roleId;
}
