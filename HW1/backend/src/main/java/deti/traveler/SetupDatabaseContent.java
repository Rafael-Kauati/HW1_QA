package deti.traveler;


import deti.traveler.entity.Travel;
import deti.traveler.service.TravelService;
import deti.traveler.service.utils.CURRENCY;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class SetupDatabaseContent
{
    @Autowired
    private TravelService service;

    public void setup() throws IOException, InterruptedException {
        Travel t1 = new Travel(null, "CityA", "CityB", LocalDate.of(2024, 3, 25),
                LocalDate.of(2024, 3, 26), 20, 15.0);
        if (service.getTravel(t1.getFromcity(), t1.getTocity(), t1.getDeparture(), t1.getNumseats(), CURRENCY.EUR).isEmpty()) {
            log.info("Inserting : "+t1);
            service.addTravel(t1);
        }

        Travel t2 = new Travel(null, "CityB", "CityA", LocalDate.of(2024, 3, 25),
                LocalDate.of(2024, 3, 25), 11, 25.0);
        if (service.getTravel(t2.getFromcity(), t2.getTocity(), t2.getDeparture(), t2.getNumseats(), CURRENCY.EUR).isEmpty()) {
            log.info("Inserting : "+t2);
            service.addTravel(t2);
        }

        Travel t3 = new Travel(null, "CityC", "CityD", LocalDate.of(2024, 3, 25),
                LocalDate.of(2024, 3,20), 20, 10.0);
        if (service.getTravel(t3.getFromcity(), t3.getTocity(), t3.getDeparture(), t3.getNumseats(), CURRENCY.EUR).isEmpty()) {
            log.info("Inserting : "+t3);
            service.addTravel(t3);
        }

        Travel t4 = new Travel(null, "Aveiro", "Porto", LocalDate.of(2024, 7, 11),
                LocalDate.of(2024, 7,12), 11, 15.0);
        if (service.getTravel(t4.getFromcity(), t4.getTocity(), t4.getDeparture(), t4.getNumseats(), CURRENCY.EUR).isEmpty()) {
            log.info("Inserting : "+t4);
            service.addTravel(t4);
        }


        Travel t5 = new Travel(null, "Aveiro", "Paris", LocalDate.of(2024, 7, 11),
                LocalDate.of(2024, 7,14), 30, 15.0);
        if (service.getTravel(t5.getFromcity(), t5.getTocity(), t5.getDeparture(), t5.getNumseats(), CURRENCY.EUR).isEmpty()) {
            log.info("Inserting : "+t5);
            service.addTravel(t5);
        }

    }



}
