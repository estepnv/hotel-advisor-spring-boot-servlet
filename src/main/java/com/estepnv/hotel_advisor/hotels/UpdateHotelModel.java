package com.estepnv.hotel_advisor.hotels;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdateHotelModel {
    private Optional<String> name;
}
