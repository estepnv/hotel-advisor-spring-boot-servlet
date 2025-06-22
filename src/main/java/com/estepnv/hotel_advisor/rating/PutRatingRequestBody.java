package com.estepnv.hotel_advisor.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PutRatingRequestBody {
    @NotNull
    @Min(0)
    @Max(5)
    private Double rating;
}
