package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.hotels.HotelRepository;
import com.estepnv.hotel_advisor.hotels.HotelService;
import com.estepnv.hotel_advisor.hotels.UpdateHotelModel;
import com.estepnv.hotel_advisor.iam.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    public Rating fetchRating(UUID userId, UUID hotelId) {
        return ratingRepository.findByUserIdAndHotelId(userId, hotelId)
                .orElseThrow(() -> new RecordNotFoundException("Rating", "userId=%s hotelId=%s".formatted(userId, hotelId)));
    }

    @Transactional
    public Rating putRating( PutRatingModel model) {
        var maybeRatingRecord = ratingRepository.findByUserIdAndHotelId(model.getActorUserId(), model.getHotelId());

        Rating record;

        if (maybeRatingRecord.isPresent()) {
            record = maybeRatingRecord.get();
        } else {
            record = new Rating();

            var user = userRepository.findById(model.getActorUserId())
                    .orElseThrow(() -> new RecordNotFoundException("User", model.getActorUserId().toString()));

            var hotel = hotelRepository.findById(model.getHotelId())
                    .orElseThrow(() -> new RecordNotFoundException("Hotel", model.getHotelId().toString()));

            record.setUserId(user.getId());
            record.setHotelId(hotel.getId());
        }

        var averageRating = ratingRepository.getAverageHotelRating(model.getHotelId());
        var updateHotelModel = new UpdateHotelModel();
        updateHotelModel.setRatingCache(Optional.of(averageRating));
        hotelService.updateHotel(model.getHotelId(), updateHotelModel);

        record.setValue(model.getRating());
        ratingRepository.save(record);
        return ratingRepository.findById(record.getId()).orElseThrow();
    }
}
