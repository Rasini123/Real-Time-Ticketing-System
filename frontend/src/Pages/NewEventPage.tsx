import React, { useState } from "react";
import axios from "axios";
import "./NewEventPage.css";

export default function NewEventPage() {
    // State to hold form values
    const [formData, setFormData] = useState({
        eventName: "",
        eventLocation: "",
        date: "",
        ticketPrice: "",
        totalTickets: "",
        ticketReleaseRate: "",
        customerRetrieveRate: "",
        maxTicketCapacity: "",
    });

    // Handle input changes
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    // Handle form submission
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Get userId from localStorage
        const userId = localStorage.getItem("user-id");
        if (!userId) {
            alert("User is not logged in.");
            return;
        }

        if (parseInt(formData.totalTickets) < parseInt(formData.maxTicketCapacity)){
            alert("Total tickets should be greater than max tickets")
            return
        } else if (parseInt(formData.ticketReleaseRate) < 0 || parseInt(formData.ticketReleaseRate) > 10){
            alert("Ticket release rate should be between 1 to 10s")
            return
        } else if (parseInt(formData.customerRetrieveRate) < 0 || parseInt(formData.customerRetrieveRate) > 10){
            alert("Customer retrivel rate should be between 1 to 10s")
            return
        }

        const payload = {
            ...formData,
            ticketPrice: parseFloat(formData.ticketPrice),
            totalTickets: parseInt(formData.totalTickets),
            ticketReleaseRate: parseInt(formData.ticketReleaseRate),
            customerRetrieveRate: parseInt(formData.customerRetrieveRate),
            maxTicketCapacity: parseInt(formData.maxTicketCapacity),
            userId: parseInt(userId),
        };

        console.log(payload);

        try {
            const response = await axios.post(
                "http://localhost:8080/api/vendor/create-event",
                payload,
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            if (response.status === 200 || response.status === 201) {
                alert("Event registered successfully!");
                console.log(response.data);
            }
        } catch (error: any) {
            if (error.response) {
                // Server responded with a status other than 200
                alert("Failed to register event: " + error.response.data.message);
            } else {
                // Network error or other issues
                console.error("Error submitting form:", error);
                alert("An error occurred. Please try again later.");
            }
        }
    };

    return (
        <div className="wrapper">
            <form onSubmit={handleSubmit}>
                <h1>Registration</h1>

                <div className="input-box">
                    <div className="input-field">
                        <input
                            type="text"
                            name="eventName"
                            placeholder="Event Name"
                            value={formData.eventName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="text"
                            name="eventLocation"
                            placeholder="Event Location"
                            value={formData.eventLocation}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <div className="input-box">
                    <div className="input-field">
                        <input
                            type="date"
                            name="date"
                            value={formData.date}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="number"
                            name="ticketPrice"
                            placeholder="Ticket Price"
                            value={formData.ticketPrice}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <div className="input-box">
                    <div className="input-field">
                        <input
                            type="number"
                            name="totalTickets"
                            placeholder="Total Tickets"
                            value={formData.totalTickets}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="number"
                            name="ticketReleaseRate"
                            placeholder="Ticket Release Rate"
                            value={formData.ticketReleaseRate}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <div className="input-box">
                    <div className="input-field">
                        <input
                            type="number"
                            name="customerRetrieveRate"
                            placeholder="Customer Retrieval Rate"
                            value={formData.customerRetrieveRate}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="number"
                            name="maxTicketCapacity"
                            placeholder="Max Ticket Capacity"
                            value={formData.maxTicketCapacity}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <button type="submit" className="btn">Register</button>
            </form>
        </div>
    );
}
