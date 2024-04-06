package deti.traveler.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Travel")
@Getter
@Setter
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fromcity")
    private String fromcity;

    @Column(name = "tocity")
    private String tocity;

    @Column(name = "departure")
    private LocalDate departure;

    @Column(name = "arrive")
    private LocalDate arrive;

    @Column(name = "numseats")
    private int numseats;

    @Column(name = "price")
    private Double price;

    public void bookSeats(final int numSeatsBooked)
    {
        setNumseats(
                getNumseats() - numSeatsBooked
        );
    }

}
