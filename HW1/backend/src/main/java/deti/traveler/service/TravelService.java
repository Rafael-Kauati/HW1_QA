package deti.traveler.service;

import deti.traveler.cache.TTLCurrencyCache;
import deti.traveler.entity.Ticket;
import deti.traveler.entity.Travel;
import deti.traveler.entity.TravelTicketDTO;
import deti.traveler.repository.TicketRepository;
import deti.traveler.repository.TravelRepository;
import deti.traveler.service.utils.CURRENCY;
import deti.traveler.service.utils.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelService
{

    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final TravelRepository travelRepository;

    private TTLCurrencyCache converter = null;

    public void addTravel(final Travel t) throws IOException, InterruptedException {
        travelRepository.save(t);
        getTravel(t.getFromcity(), t.getTocity()
        , t.getDeparture(), t.getNumseats(), CURRENCY.EUR);

    }


    public List<Travel> getTravel(final String from, final String to, final LocalDate departure, final int numSeats, CURRENCY currency) throws IOException, InterruptedException {
        if (converter == null)
        {
            converter = new TTLCurrencyCache(new CurrencyConverter());
            converter.setTTL(40000);
        }
        log.info("\nFetching a travel : "+ from+" -> "+to+", with atleast "+numSeats+" avaible | to pay in "+currency);
        List<Travel> travelsFound =  travelRepository.findByFromcityAndTocityAndDepartureAndNumseatsIsGreaterThanEqual(from, to, departure, numSeats);

        if(!travelsFound.isEmpty()) {log.info("\nFound : \n"+travelsFound);}
        else {
            log.error("\nNot Found !!!");
        }

        for(Travel t : travelsFound)
        {
            t.setPrice(converter.convertValue(currency, t.getPrice()));
        }

        return travelsFound;
    }

    public Ticket purchaseTicket(final Long id, String owner, int numSeatsBooked) {
        final Optional<Travel> travel = travelRepository.findById(id);
        log.info("\nTravel fetched : " + travel.get().toString() + " with id : " + id);
        final Ticket ticket = new Ticket().builder()
                .purchasedAt(LocalDateTime.now())
                .travel(travel.get())
                .numOfSeats(numSeatsBooked)
                .owner(owner)
                .build();
        log.info("\nTicket booked by : " + owner + ", num of seats booked : " + numSeatsBooked);
        ticketRepository.save(ticket);
        travel.get().bookSeats(
                 numSeatsBooked
        );
        travelRepository.save(travel.get());
        return ticket;
    }


    public List<TravelTicketDTO> retrieveTickets(final String owner)
    {
        final List<TravelTicketDTO> result = ticketRepository.findTicketDetails(owner);

        if(result.isEmpty())
        {
            log.warn("No tickets found for passager : "+owner);
        }
        else{
            log.info("Tickets found for passager "+owner+" :\n"+result);
        }

        return result;
    }



}
