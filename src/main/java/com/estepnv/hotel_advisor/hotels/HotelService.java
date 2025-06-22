package com.estepnv.hotel_advisor.hotels;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    public Hotel createHotel(CreateHotelModel model) {
        var newRecord = new Hotel();
        newRecord.setName(model.getName());
        newRecord.setRatingCache(0.0);

        return hotelRepository.save(newRecord);
    }

    public Hotel updateHotel(UUID id, UpdateHotelModel model) {
        var hotel = hotelRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Hotel", id.toString()));

        model.getName().ifPresent(value -> hotel.setName(value));
        model.getRatingCache().ifPresent(value -> hotel.setRatingCache(value));

        return hotelRepository.save(hotel);
    }

    public void removeHotel(UUID id) {
        var maybeHotel = hotelRepository.findById(id);

        if (maybeHotel.isPresent()) {
            hotelRepository.delete(maybeHotel.get());
        }
    }
}
