import React, { useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import Select from "react-select";
const BookingForm = ({ searchCallback, currencyCallback}) => {
  const [departureCity, setDepartureCity] = useState('');
  const [destinationCity, setDestinationCity] = useState('');
  const [departureDate, setDepartureDate] = useState(new Date);
  const [currency, setCurrency] = useState(null)
  //new Date().setFullYear(2024,03,25)
  const [numSeats, setNumSeats] = useState(1);


  const handleSubmit = async (e) => {
    e.preventDefault();
    const apiUrl = `http://localhost:9090/cities/${currency.value}?fromCity=${departureCity}&toCity=${destinationCity}&departure=${departureDate}&numSeats=${numSeats}`;
  
    const response = await axios.get(apiUrl);

    searchCallback(response.data)
    currencyCallback(currency.value)
  };

  const currency_options = [
    { value: "EUR", label: "EUR" },
    { value: "USD", label: "USD" },
    { value: "JPY", label: "JPY" },
    { value: "GBP", label: "GBP" },
  ];
  

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Departure City:
        <input
          type="text"
          value={departureCity}
          onChange={(e) => setDepartureCity(e.target.value)}
          required
        />
      </label>
      
      <label>
        Destination City:
        <input
          type="text"
          value={destinationCity}
          onChange={(e) => setDestinationCity(e.target.value)}
          required
        />
      </label>
      <br />
      <label>
        Departure Date:
        <input
          type="date"
          value={departureDate}
          onChange={(e) => setDepartureDate(e.target.value)}
          required
        />
      </label>
      <label>
        Number of Seats:
        <input
          type="number"
          value={numSeats}
          onChange={(e) => setNumSeats(parseInt(e.target.value))}
          min="1"
          required
        />
      </label>

      <label>
      <Select id='currencySelector'
        defaultValue={currency}
        onChange={setCurrency}
        options={currency_options}
      />
      </label>
      <br />
      <button type="submit">Search</button>
    </form>
  );
};

BookingForm.propTypes = {
  searchCallback: PropTypes.func.isRequired,
  currencyCallback: PropTypes.func.isRequired
};

export default BookingForm;
