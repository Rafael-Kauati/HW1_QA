package deti.traveler.repository;
import deti.traveler.entity.Travel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DataJpaTest
@Disabled
class TravelRepositoryTest {

    @Mock
    private TravelRepository travelRepository;

    @Test
     void testFindByFromCityAndToCityAndDepartureAndNumSeats() {
        String fromCity = "CityA";
        String toCity = "CityB";
        LocalDate departure = LocalDate.of(2024, 4, 8);
        int numSeats = 5;

        Travel travel1 = new Travel(1L, "CityA", "CityB", departure, departure.plusDays(1), 10, 100.0);
        Travel travel2 = new Travel(2L, "CityA", "CityB", departure, departure.plusDays(1), 8, 90.0);
        List<Travel> expectedTravels = Arrays.asList(travel1, travel2);

        when(travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity, toCity, departure, numSeats))
                .thenReturn(expectedTravels);

        List<Travel> foundTravels = travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity, toCity, departure, numSeats);

        assertEquals(2, foundTravels.size());
        assertEquals(1L, foundTravels.get(0).getId());
        assertEquals(2L, foundTravels.get(1).getId());
    }
}
