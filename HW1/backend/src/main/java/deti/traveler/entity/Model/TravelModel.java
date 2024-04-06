package deti.traveler.entity.Model;

import jakarta.json.bind.annotation.JsonbTypeSerializer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TravelModel
{
    private final String fromCity, toCity;
    private final LocalDate departure;
    private final int numSeats;

}
