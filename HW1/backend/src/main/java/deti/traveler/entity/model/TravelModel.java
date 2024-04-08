package deti.traveler.entity.model;


import java.time.LocalDate;

public record TravelModel(String fromCity, String toCity, LocalDate departure, int numSeats) {
}
