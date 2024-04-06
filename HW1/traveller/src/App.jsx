import React, { useState, useEffect } from 'react';
import './App.css';
import BookingForm from './components/BookingForm/BookingForm';
import axios from 'axios';

function App() {
  const [searchData, setSearchData] = useState([]);
  const [currencyOpt, setCurrencyOpt] = useState(null);
  const [selectedTravel, setSelectedTravel] = useState(null); // To store the selected travel item
  const [numSeatsToPurchase, setNumSeatsToPurchase] = useState(1); // To store the number of seats to purchase
  const [tickets, setTickets] = useState([]); // To store the ticket data

  useEffect(() => {
    // Fetch tickets when the component mounts
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    // Fetch tickets from the endpoint
    axios.get(`http://localhost:9090/tickets/JamesLee`)
      .then(response => {
        setTickets(response.data);
        console.log(tickets)
      })
      .catch(error => {
        console.error('Error fetching tickets:', error);
      });
  };

  const SearchTravelHandler = (data) => {
    setSearchData(data);
  };

  const handlePurchase = (travel) => {
    // Set the selected travel item
    setSelectedTravel(travel);
    // Here you can navigate to a new page or open a modal for the purchase
    console.log('Purchase:', travel);
  };

  const handleNumSeatsChange = (e) => {
    const numSeats = parseInt(e.target.value);
    if (!isNaN(numSeats) && numSeats >= 1 && numSeats <= selectedTravel.numseats) {
      setNumSeatsToPurchase(numSeats);
    }
  };

  const handlePurchaseTicket = (e) => {
    e.preventDefault();
    if (!selectedTravel) return; // Prevent executing if no travel is selected
    const apiPurchaseUrl = `http://localhost:9090/purchase/${selectedTravel.id}?owner=JamesLee&numSeatsBooked=${numSeatsToPurchase}`;
    axios.get(apiPurchaseUrl)
      .then(response => {
        console.log(response.data);
        // After purchasing, fetch updated tickets
        fetchTickets();
      })
      .catch(error => {
        console.error('Error purchasing ticket:', error);
      });
  }

  return (
    <div className="container">
      <BookingForm searchCallback={SearchTravelHandler} currencyCallback={setCurrencyOpt} />
      <div className="travel-list">
        {searchData.length === 0 ? (
          <p className="no-results">No search results available.</p>
        ) : (
          <ul>
            {searchData.map((item) => (
              <li key={item.id} className="travel-item">
                <div className="travel-details">
                  <p className="from-to">
                    From {item.fromcity} to {item.tocity}
                  </p>
                  <p className="date">Departure Date: {item.departure}</p>
                  <p className="date">Arrival Date: {item.arrive}</p>
                  <p className="seats">Seats availables: {item.numseats}</p>
                  <p className="price">Price: ${item.price.toFixed(2)} {currencyOpt}</p>
                  <button className='buttonItem' onClick={() => handlePurchase(item)}>Purchase</button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
      {/* Modal or Order Window */}
      {selectedTravel && (
        <div className="order-window">
          <h2>Order Details</h2>
          <p>From: {selectedTravel.fromcity}</p>
          <p>To: {selectedTravel.tocity}</p>
          <p>Departure : {selectedTravel.departure}</p>
          <p>Arrival : {selectedTravel.arrive}</p>
          <p>Seats availables: {selectedTravel.numseats}</p>
          <p>Price: ${selectedTravel.price.toFixed(2)} ${currencyOpt}</p>
          <label htmlFor="numSeats">Number of Seats to Purchase:</label>
          <input
            type="number"
            id="numSeats"
            name="numSeats"
            min="1"
            max={selectedTravel.numseats}
            value={numSeatsToPurchase}
            onChange={handleNumSeatsChange}
          />
          <button className='buttonItem' onClick={handlePurchaseTicket}>Purchase</button>
          <button onClick={() => setSelectedTravel(null)}>Close</button>
        </div>
      )}
      {/* Table for tickets */}
      <div className="ticket-list">
        <h2>Tickets</h2>
        {tickets.length === 0 ? (
          <p>No tickets available.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Ticket ID</th>
                <th>From City</th>
                <th>To City</th>
                <th>Departure Date</th>
                <th>Arrival Date</th>
                <th>Number of Seats</th>
                <th>Price </th>
              </tr>
            </thead>
            <tbody>
              {tickets.map(ticket => (
                <tr key={ticket.id}>
                  <td>{ticket.id}</td>
                  <td>{ticket.fromCity}</td>
                  <td>{ticket.toCity}</td>
                  <td>{ticket.departure}</td>
                  <td>{ticket.arrive}</td>
                  <td>{ticket.numOfSeats}</td>
                  <td>${ticket.price.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default App;
