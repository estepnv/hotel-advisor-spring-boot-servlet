package com.estepnv.hotel_advisor.hotels;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdateHotelModel {
    private Optional<String> name;
    private Optional<Double> ratingCache;

    public UpdateHotelModel() {
        this.name = Optional.ofNullable(null);
        this.ratingCache = Optional.ofNullable(null);
    }
}
