package deti.traveler.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class TravelTicketDTO {
    private Long ticketId;
    private String owner;
    private LocalDateTime purchasedAt;
    private Long travelId;
    private Integer numOfSeats;
    private LocalDate arrive;
    private LocalDate departure;
    private String fromCity;
    @Setter
    private Double price;
    private String toCity;

    public TravelTicketDTO(Long ticketId, String owner, LocalDateTime purchasedAt, Long travelId, int numOfSeats, LocalDate arrive, LocalDate departure, String fromCity, Double price, String toCity) {
        this.ticketId = ticketId;
        this.owner = owner;
        this.purchasedAt = purchasedAt;
        this.travelId = travelId;
        this.numOfSeats = numOfSeats;
        this.arrive = arrive;
        this.departure = departure;
        this.fromCity = fromCity;
        this.price = price;
        this.toCity = toCity;
    }

}
