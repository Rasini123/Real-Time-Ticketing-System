import React, { useEffect, useState } from "react";
import axios from "axios";
import "./EventsPage.css";
import { useNavigate } from "react-router-dom";

interface Event {
    eventId: number;
    eventName: string;
    eventLocation: string;
    ticketPrice: number;
    date: string,
    customerRetrieveRate: number,
    ticketReleaseRate: number,
    maxTicketCapacity: number
    totalTickets: number, 
    
}

export default function EventsPage() {
    const [events, setEvents] = useState<Event[]>([]);
    const id = window.localStorage.getItem("user-id");
    const nav = useNavigate()

    useEffect(() => {
        const fetchEvents = async () => {
            if (!id) {
                alert("User ID not found in localStorage");
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/api/vendor/active-events/${id}`);
                setEvents(response.data); // Ensure the response is typed correctly in your backend
                console.log(response.data);
            } catch (error) {
                console.error("Error fetching events:", error);
                alert("Failed to load events. Please try again later.");
            }
        };

        fetchEvents();
    }, [id]);

    const poolRedirect = (eventId: number) => {
        nav("/vendor/events/" + eventId)
    }

    return (
        <section className="container">
            {events.length > 0 ? (
                events.map((event) => (
                    <div key={event.eventId} className="card">
                        <h2>{event.eventName}</h2>
                        <p>{event.eventLocation} <br/>Rs {event.ticketPrice} <br/>{event.date}</p>
                        <div>Customer Retrivel Rate - {event.customerRetrieveRate}</div>
                        <div>Release Rate - {event.ticketReleaseRate}</div>
                        <div>Total - {event.totalTickets}</div>
                        <div>Max Pool - {event.maxTicketCapacity}</div>
                        <a onClick={() => poolRedirect(event.eventId)}>See Pool</a>
                    </div>
                ))
            ) : (
                <p>Loading events...</p>
            )}
        </section>
    );
}
