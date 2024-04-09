package deti.traveler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import deti.traveler.entity.Ticket;
import deti.traveler.entity.Travel;
import deti.traveler.entity.TravelTicketDTO;
import deti.traveler.service.TravelService;
import deti.traveler.service.utils.CURRENCY;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.MvcResult;

//@WebMvcTest(controllers = TravelController.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
class ControllerTest
{
    @Autowired
    private MockMvc mockController;

    @MockBean
    private TravelService service;

    private final Travel DummyTravel = new Travel(1L, "Dublin, Ireland","Galway, Ireland",LocalDate.now(),  LocalDate.now(), 6, 11.99);

    private final Ticket DummyTicket = Ticket.builder()
            .owner("James Lee")
            .travel(DummyTravel)
            .purchasedAt(LocalDateTime.now())
            .build();


    @Test
    void testSearchByGivenCities() throws Exception {
        MvcResult result = mockController.perform(get("/cities/EUR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fromCity", "Dublin, Ireland")
                        .param("toCity", "Galway, Ireland")
                        .param("departure", LocalDate.now().toString())
                        .param("numSeats", "6")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Dublin, Ireland"));
        assertTrue(content.contains("Galway, Ireland"));
    }
    @Test
    void testGetTravelsBetweenCitiesNotFound() throws Exception {


        MvcResult result = mockController.perform(get("/cities/EUR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fromCity", "Paris, France")
                        .param("toCity", "Madrid, Spain")
                        .param("departure", String.valueOf(LocalDate.now()))
                        .param("numSeats", String.valueOf(6))
                )
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(404, statusCode);
    }



    @Test
    void testPurchaseTicketForATravel() throws Exception {

        //'http://localhost:9090/purchase/1?owner=JohnDoe&numSeatsBooked=2'

        MvcResult result = mockController.perform(get("/purchase/1")
                        .param("owner", "JohnDoe")
                        .param("numSeatsBooked", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(201, statusCode);
    }

    @Test
    void testGetTicketsByOwner() throws Exception {
        MvcResult result = mockController.perform(get("/tickets/JohnDoe?currency=EUR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].ticketId").value(1))
                .andExpect(jsonPath("$[0].owner").value("JohnDoe"))
                .andExpect(jsonPath("$[0].numOfSeats").value(2))
                .andExpect(jsonPath("$[1].ticketId").value(2))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"ticketId\":1"));
        assertTrue(content.contains("\"owner\":\"JohnDoe\""));
        assertTrue(content.contains("\"numOfSeats\":2"));
        assertTrue(content.contains("\"ticketId\":2"));
    }




    @BeforeEach
    void setupTest() throws IOException, InterruptedException {
        final List<Travel> trips = new ArrayList<>();
        trips.add(DummyTravel);

        when(service.purchaseTicket(
                eq(DummyTravel.getId()),
                eq(DummyTicket.getOwner()),
                anyInt()
        )).thenReturn(DummyTicket);

        when(service.getTravel(
                eq(DummyTravel.getFromcity()),
                eq(DummyTravel.getTocity()),
                any(LocalDate.class),
                eq(DummyTravel.getNumseats()),
                eq(CURRENCY.EUR)
        )).thenReturn(trips);
        TravelTicketDTO td1 = TravelTicketDTO.builder()
                .price(11.99).toCity("Galway, Ireland").fromCity("Dublin, Ireland")
                .arrive( LocalDate.now()).departure( LocalDate.now()).ticketId(1L)
                .owner("JohnDoe")
                .travelId(1L).numOfSeats(2).purchasedAt(LocalDateTime.now())
                .build();

        TravelTicketDTO td2 = TravelTicketDTO.builder()
                .price(11.99).toCity("London, UK").fromCity("Paris, France")
                .arrive( LocalDate.now()).departure( LocalDate.now()).ticketId(2L)
                .owner("JohnDoe")
                .travelId(2L).numOfSeats(1).purchasedAt(LocalDateTime.now())
                .build();

        List<TravelTicketDTO> tickets = Arrays.asList(
                td1,
                td2
        );
        when(service.retrieveTickets(anyString(), any(CURRENCY.class))).thenReturn(tickets);


        String fromCity = "Paris, France";
        String toCity = "Madrid, Spain";
        int numSeats = 6;

        when(service.getTravel(eq(fromCity), eq(toCity), any(LocalDate.class), eq(numSeats), any(CURRENCY.class)))
                .thenReturn(Collections.emptyList());
    }

}
