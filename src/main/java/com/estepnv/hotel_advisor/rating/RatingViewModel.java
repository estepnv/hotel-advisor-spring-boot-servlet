package com.estepnv.hotel_advisor.rating;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

@Data
public class RatingViewModel extends RepresentationModel<RatingViewModel> {
    private UUID hotelId;
    private UUID userId;
    private Double rating;
    private Instant createdAt;
    private Instant updatedAt;
}
