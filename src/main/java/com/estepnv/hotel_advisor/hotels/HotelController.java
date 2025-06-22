package com.estepnv.hotel_advisor.hotels;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    HotelViewModelAssembler modelAssembler;

    @Autowired
    PagedResourcesAssembler<Hotel> pagedResourcesAssembler;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/api/hotels")
    public ResponseEntity<RepresentationModel<?>> createHotel(@Valid @RequestBody CreateHotelModel model) {
        var hotel = hotelService.createHotel(model);
        return ResponseEntity.status(201).body(modelAssembler.toModel(hotel));
    }

    @GetMapping("/api/hotels")
    public ResponseEntity<PagedModel<?>> listHotels(Pageable pageable) {
        var hotels = hotelRepository.findAll(pageable);
        var model = pagedResourcesAssembler.toModel(hotels, modelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/api/hotels/{id}")
    public ResponseEntity<RepresentationModel<?>> show(@PathVariable UUID id){
        var hotel = hotelRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("Hotel", id.toString()));

        return ResponseEntity.status(200).body(modelAssembler.toModel(hotel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/api/hotels/{id}")
    public ResponseEntity<RepresentationModel<?>> update(@PathVariable UUID id, @Valid @RequestBody UpdateHotelModel model) {
        var hotel = hotelService.updateHotel(id, model);
        return ResponseEntity.status(200).body(modelAssembler.toModel(hotel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/api/hotels/{id}")
    public ResponseEntity<RepresentationModel<?>> delete(@PathVariable UUID id) {
        hotelService.removeHotel(id);
        return ResponseEntity.noContent().build();
    }
}
