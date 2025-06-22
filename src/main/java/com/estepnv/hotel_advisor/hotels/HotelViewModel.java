package com.estepnv.hotel_advisor.hotels;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class HotelViewModel extends RepresentationModel<HotelViewModel> {
    private UUID id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
