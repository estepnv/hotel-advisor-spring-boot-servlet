package com.estepnv.hotel_advisor.hotels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHotelModel {
    @NotBlank
    @NotNull
    private String name;
}
