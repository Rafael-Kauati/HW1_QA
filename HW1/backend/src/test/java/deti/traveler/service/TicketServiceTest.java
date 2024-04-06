package deti.traveler.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import deti.traveler.entity.Ticket;
import deti.traveler.entity.Travel;
import deti.traveler.entity.TravelTicketDTO;
import deti.traveler.repository.TicketRepository;
import deti.traveler.repository.TravelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TravelRepository travelRepository;

    @InjectMocks
    private TravelService ticketService;


    @Captor
    private ArgumentCaptor<String> ownerCaptor;

    @Test
    void testPurchaseTicket() {
        // Mock data
        Long travelId = 1L;
        String owner = "John Doe";
        LocalDateTime now = LocalDateTime.now();
        Travel travel = new Travel();
        travel.setId(travelId);
        travel.setNumseats(10); // Assuming there are 10 seats available
        Optional<Travel> optionalTravel = Optional.of(travel);

        // Mock behavior
        when(travelRepository.findById(travelId)).thenReturn(optionalTravel);

        // Method call
        Ticket purchasedTicket = ticketService.purchaseTicket(travelId, owner, 1);

        // Verification
        assertNotNull(purchasedTicket);
        assertEquals(owner, purchasedTicket.getOwner());
        assertNotNull(purchasedTicket.getPurchasedAt());
        assertEquals(travel, purchasedTicket.getTravel()); // Assuming Ticket has appropriate equals() method

        verify(ticketRepository, times(1)).save(purchasedTicket);
        optionalTravel.get().bookSeats(
                 1
        );
        verify(travelRepository, times(1)).save(optionalTravel.get());
    }


    @Test
    void testRetrieveTickets() {
        String owner = "John Doe";
        List<TravelTicketDTO> expectedTickets = new ArrayList<>();
        TravelTicketDTO ticket1 = new TravelTicketDTO(1L, owner, LocalDateTime.now(), 1L, 2, LocalDate.now(), LocalDate.now(), "CityA", 100.0, "CityB");        expectedTickets.add(ticket1);

        when(ticketRepository.findTicketDetails(owner)).thenReturn(expectedTickets);

        List<TravelTicketDTO> actualTickets = ticketService.retrieveTickets(owner);

        assertNotNull(actualTickets);
        assertEquals(expectedTickets.size(), actualTickets.size());
        for (int i = 0; i < expectedTickets.size(); i++) {
            assertEquals(expectedTickets.get(i).getTicketId(), actualTickets.get(i).getTicketId());
        }

        verify(ticketRepository).findTicketDetails(ownerCaptor.capture());
        assertEquals(owner, ownerCaptor.getValue());
    }
}
