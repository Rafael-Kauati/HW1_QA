package deti.traveler.controller;

import deti.traveler.entity.Ticket;
import deti.traveler.entity.Travel;
import deti.traveler.entity.TravelTicketDTO;
import deti.traveler.service.TravelService;
import deti.traveler.service.utils.CURRENCY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
public class TravelController {
    @Autowired
    private  TravelService service;


    //Option deixar de usar rquest body e usar apenas RquestParam
    @GetMapping("/cities/{currency}")
    public ResponseEntity<List<Travel>> getTravelsBetweenCities(
            @RequestParam("fromCity") String fromCity,
            @RequestParam("toCity") String toCity,
            @RequestParam("departure") LocalDate departure,
            @RequestParam("numSeats") int numSeats,
            @PathVariable CURRENCY currency) throws IOException, InterruptedException {

        final List<Travel> result = service.getTravel(fromCity, toCity, departure, numSeats, currency);
        return !result.isEmpty() ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Ticket> purchaseTravel(@PathVariable Long id,@RequestParam("owner") String owner,@RequestParam("numSeatsBooked") int numSeatsBooked) {
        return new ResponseEntity<>(service.purchaseTicket(id, owner, numSeatsBooked), HttpStatus.CREATED);
    }

    @GetMapping("/tickets/{owner}")
    public ResponseEntity<List<TravelTicketDTO>> getTicketsByOwner(@PathVariable String owner, @RequestParam("currency") CURRENCY currency) throws IOException, InterruptedException {
        return new ResponseEntity<>(service.retrieveTickets(owner, currency), HttpStatus.OK);
    }
}