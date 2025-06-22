package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.hotels.HotelController;
import com.estepnv.hotel_advisor.users.UserController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;

@Component
@Relation(collectionRelation = "ratings", itemRelation = "rating")
public class RatingModelAssembler implements RepresentationModelAssembler<Rating, RatingViewModel> {
    @Override
    public RatingViewModel toModel(Rating record) {
        var model = new RatingViewModel();
        model.setRating(record.getValue());
        model.setCreatedAt(record.getCreatedAt().toInstant());
        model.setUpdatedAt(record.getUpdatedAt().toInstant());
        model.setHotelId(record.getHotelId());
        model.setUserId(record.getUserId());

        model.add(linkTo(methodOn(RatingController.class).show(record.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).show(record.getUserId())).withRel("user"));
        model.add(linkTo(methodOn(HotelController.class).show(record.getHotelId())).withRel("hotel"));

        return model;
    }
}
