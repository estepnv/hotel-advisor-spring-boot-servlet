package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.iam.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PutRatingModel {
    @Min(0)
    @Max(5)
    private Double rating;

    private UUID actorUserId;

    private UUID hotelId;
}
