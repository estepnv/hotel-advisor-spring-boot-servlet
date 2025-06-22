package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.iam.UserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    private final RatingModelAssembler ratingModelAssembler;

    @GetMapping("/api/hotels/{hotel_id}/current_user_rating")
    ResponseEntity<RepresentationModel<?>> show(@PathVariable("hotel_id") UUID hotelId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var rating = ratingService.fetchRating(UUID.fromString(authentication.getName()), hotelId);

        return ResponseEntity.ok(ratingModelAssembler.toModel(rating));
    }

    @PutMapping ("/api/hotels/{hotel_id}/rate")
    ResponseEntity<RepresentationModel<?>> putRating(@PathVariable("hotel_id") UUID hotelId, @Valid @RequestBody PutRatingRequestBody params) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var model = new PutRatingModel();
        model.setRating(params.getRating());
        model.setActorUserId(UUID.fromString(authentication.getName()));
        model.setHotelId(hotelId);

        Rating rating = ratingService.putRating(model);

        return ResponseEntity.status(201).body(ratingModelAssembler.toModel(rating));
    }


}
