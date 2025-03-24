import React, { useEffect, useState } from "react";
import axios from "axios";
import "./VendorHistory.css";

interface Vendor {
    eventName: string,
    eventDate: string,
    eventLocation: string,
    ticketPrice: number,
    eventId: number,
    date: string
}

export default function VendorHistory() {
    const [vendors, setVendors] = useState<Vendor[]>([]);
    const userId = window.localStorage.getItem("user-id")

    useEffect(() => {
        const fetchVendors = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/vendor/inactive-events/" + userId); // Update API URL
                console.log(response)
                setVendors(response.data); // Ensure the response matches Vendor interface
            } catch (error) {
                console.error("Error fetching vendors:", error);
                alert("Failed to load vendor data. Please try again later.");
            }
        };

        fetchVendors();
    }, []);

    return (
        <div className="container">
            <div className="tbl_container">
                <h2 style={{textAlign:"center", margin:"20px"}}>Vendor History</h2>
                <table className="tbl">
                    <thead>
                        <tr>
                            <th>Event Id</th>
                            <th>Event Name</th>
                            <th>Date</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {vendors.length > 0 ? (
                            vendors.map((vendor) => (
                                <tr key={vendor.eventId}>
                                    <td data-label="Event Id">{vendor.eventId}</td>
                                    <td data-label="Event Name">{vendor.eventName}</td>
                                    <td data-label="Date">{vendor.date}</td>
                                    <td data-label="Price">{vendor.ticketPrice}</td>
                               
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={6}>Loading vendor data...</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
