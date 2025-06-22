package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.hotels.Hotel;
import com.estepnv.hotel_advisor.iam.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
    Optional<Rating> findByUserIdAndHotelId(UUID userId, UUID hotelId);

    @Query("SELECT COALESCE(AVG(r.value), 0.0) FROM Rating r WHERE r.hotel.id = :hotelId")
    Double getAverageHotelRating(@Param("hotelId") UUID hotelId);
}
