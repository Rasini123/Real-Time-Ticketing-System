import React, { useEffect, useState } from "react";
import axios from "axios";
import "./EventsPage.css";
import { useNavigate } from "react-router-dom";

interface Event {
    eventId: number;
    eventName: string;
    eventLocation: string;
    ticketPrice: number;
    date: string;
}

export default function CustomerEventsPage() {
    const [events, setEvents] = useState<Event[]>([]);
    const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
    const id = window.localStorage.getItem("user-id");
    const nav = useNavigate();

    useEffect(() => {
        const fetchEvents = async () => {
            if (!id) {
                alert("User ID not found in localStorage");
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/api/customer/events`);
                setEvents(response.data); // Ensure the response is typed correctly in your backend
                console.log(response.data);
            } catch (error) {
                console.error("Error fetching events:", error);
                alert("Failed to load events. Please try again later.");
            }
        };

        fetchEvents();
    }, [id]);

    const handleQuantityChange = (eventId: number, quantity: number) => {
        setQuantities((prev) => ({
            ...prev,
            [eventId]: quantity,
        }));
    };

    const handleBuyNow = async (eventId: number) => {
        const quantity = quantities[eventId] || 1; // Default quantity to 1 if not selected

        try {
            const response = await axios.post(`http://localhost:8080/api/customer/buy-ticket/${eventId}/${quantity}/${id}`);
            if (response.status === 200) {
                alert("Purchase successful! Tickets will be added shortly !!!");
                console.log(response.data);
            } else {
                alert("Failed to complete the purchase. Please try again.");
            }
        } catch (error) {
            console.error("Error during purchase:", error);
            alert("An error occurred while processing your purchase.");
        }
    };

    return (
        <section className="container">
            {events.length > 0 ? (
                events.map((event) => (
                    <div key={event.eventId} className="card" style={{height: "280px"}}>
                        <h2>{event.eventName}</h2>
                        <p>
                            {event.eventLocation} <br />
                            Rs {event.ticketPrice} <br />
                            {event.date}
                        </p>
                        <label htmlFor={`quantity-${event.eventId}`}>Quantity:</label>
                        <input
                            type="number"
                            id={`quantity-${event.eventId}`}
                            min="1"
                            value={quantities[event.eventId] || 1}
                            onChange={(e) => handleQuantityChange(event.eventId, parseInt(e.target.value))}
                        />
                        <div className="button-group">
                            <button onClick={() => handleBuyNow(event.eventId)}>Buy Now</button>
                        </div>
                    </div>
                ))
            ) : (
                <p>Loading events...</p>
            )}
        </section>
    );
}
