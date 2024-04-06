package deti.traveler.repository;

import deti.traveler.entity.Travel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>
{
    List<Travel> findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(String fromCity, String toCity, LocalDate departure, int numSeats);

}
