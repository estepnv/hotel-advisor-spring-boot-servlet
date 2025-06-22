package com.estepnv.hotel_advisor.rating;

import com.estepnv.hotel_advisor.hotels.Hotel;
import com.estepnv.hotel_advisor.iam.User;
import com.estepnv.hotel_advisor.jpa_common.ApplicationEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(
        name = "hotel_ratings",
        uniqueConstraints = @UniqueConstraint(name ="idx_ratings_user_id_hotel_id", columnNames = {"user_id", "hotel_id"})
)
@Data
public class Rating extends ApplicationEntity {
    @NotNull
    @Column(name="hotel_id")
    private UUID hotelId;

    @NotNull
    @Column(name="rated_by_user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private Hotel hotel;

    @Max(5)
    @Min(0)
    private Double value;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_by_user_id", insertable = false, updatable = false)
    private User user;
}
