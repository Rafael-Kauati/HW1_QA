package deti.traveler.entity.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public record TravelModel(String fromCity, String toCity, LocalDate departure, int numSeats) {
}
