package com.estepnv.hotel_advisor.hotels;

import com.estepnv.hotel_advisor.jpa_common.ApplicationEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Hotel extends ApplicationEntity {
    @NotNull
    @NotBlank
    private String name;

}
