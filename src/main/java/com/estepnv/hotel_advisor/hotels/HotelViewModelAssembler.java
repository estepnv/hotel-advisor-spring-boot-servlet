package com.estepnv.hotel_advisor.hotels;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;

@Component
@Relation(collectionRelation = "hotels", itemRelation = "hotel")
public class HotelViewModelAssembler implements RepresentationModelAssembler<Hotel, HotelViewModel> {
    @Override
    public HotelViewModel toModel(Hotel entity) {
        var model = new HotelViewModel(
                entity.getId(),
                entity.getName(),
                entity.getRatingCache(),
                entity.getCreatedAt().toInstant(),
                entity.getUpdatedAt().toInstant()
        );

        model.add(linkTo(methodOn(HotelController.class).show(entity.getId())).withSelfRel());

        return model;
    }
}
