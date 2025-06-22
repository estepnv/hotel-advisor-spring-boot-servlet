package com.estepnv.hotel_advisor.iam;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);


    @EntityGraph(attributePaths = {"roles", "userRoles.role"})
    Page<User> findAll(Pageable pageable);

}
