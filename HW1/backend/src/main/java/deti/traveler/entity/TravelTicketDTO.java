package deti.traveler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelTicketDTO {
    private Long ticketId;
    private String owner;
    private LocalDateTime purchasedAt;
    private Long travelId;
    private Integer numOfSeats;
    private LocalDate arrive;
    private LocalDate departure;
    private String fromCity;
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



    public TravelTicketDTO(long l, String owner, LocalDateTime now,
                           long l1, int i, LocalDate now1, LocalDate now2,
                           String cityA, double v, String cityB) {
        this.ticketId = l;
        this.owner = owner;
        this.purchasedAt = now;
        this.travelId = l1;
        this.numOfSeats = i;
        this.arrive = now1;
        this.departure = now2;
        this.fromCity = cityA;
        this.price = v;
        this.toCity = cityB;
    }
}
