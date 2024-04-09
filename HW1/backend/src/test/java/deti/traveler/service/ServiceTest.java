package deti.traveler.service;

import deti.traveler.entity.Travel;
import deti.traveler.repository.TravelRepository;
import deti.traveler.service.utils.CURRENCY;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServiceTest
{
    @Mock
    private TravelRepository travelRepository;

    @InjectMocks
    private TravelService travelService;
    String fromCity = "CityA";
    String toCity = "CityB";
    LocalDate departure = LocalDate.of(2024, 3, 25);
    int numSeats = 2;


    List<Travel> expectedTravels = new ArrayList<>();


    @Test
    void testGetTravel() throws IOException, InterruptedException {
        List<Travel> result = travelService.getTravel(fromCity, toCity, departure, numSeats, CURRENCY.EUR);

        verify(travelRepository).findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity, toCity, departure, numSeats);

        assertEquals(expectedTravels, result);
    }

    @Test
    void testGetTravelFailure() throws IOException, InterruptedException {
        String fromCity2 = "CityX";
        String toCity2 = "CityY";
        LocalDate departureTime2 = LocalDate.of(2024, 3, 25);
        int numberOfSeats2 = 2;

        when(travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity, toCity, departure, numSeats))
                .thenThrow(new RuntimeException("Database connection failed"));

        List<Travel> result = travelService.getTravel(fromCity2, toCity2, departureTime2, numberOfSeats2, CURRENCY.EUR);

        verify(travelRepository).findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity2, toCity2, departureTime2, numberOfSeats2);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTravelExceptionHandling()  {
        String fromCity = "CityX";
        String toCity = "CityY";
        LocalDate departure = LocalDate.of(2024, 3, 25);
        int numSeats = 2;

        when(travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(anyString(), anyString(), any(LocalDate.class), anyInt()))
                .thenThrow(new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> {
            travelService.getTravel(fromCity, toCity, departure, numSeats, CURRENCY.EUR);
        });
    }

    @BeforeEach
    void setUp() {
        reset(travelRepository);

        when(travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(fromCity, toCity, departure, numSeats))
                .thenReturn(expectedTravels);

        expectedTravels.add(new Travel(1L, "CityA", "CityB", departure, null, 2, 100.0));
        expectedTravels.add(new Travel(2L, "CityA", "CityB", departure, null, 3, 120.0));

    }
}
