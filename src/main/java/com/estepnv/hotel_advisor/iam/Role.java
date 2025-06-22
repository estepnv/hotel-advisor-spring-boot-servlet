package com.estepnv.hotel_advisor.iam;

import com.estepnv.hotel_advisor.jpa_common.ApplicationEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Data
@Entity
@Table(
        name = "roles",
        uniqueConstraints = @UniqueConstraint(name ="idx_roles_name", columnNames = "name")
)
@EntityListeners(AuditingEntityListener.class)
public class Role extends  ApplicationEntity {
    @NotNull
    @NotBlank
    @Column
    private String name;

}
