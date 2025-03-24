import React, { useEffect, useState } from "react";
import axios from "axios";
import "./History.css";

// Define the shape of an order object
interface Order {
    name: string;
    location: string;
    ticketPrice: string;
    quantity: number;
    total: string;
}

export default function History() {
    const [orders, setOrders] = useState<Order[]>([]);
    const id = window.localStorage.getItem("user-id")

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/customer/tickets/" + id);
                setOrders(response.data); // Assuming response data is an array of orders
            } catch (error) {
                console.error("Error fetching orders:", error);
                alert("Failed to load orders. Please try again later.");
            }
        };

        fetchOrders();
    }, []);

    return (
        <main className="table">
            <section className="table_header">
                <h1>Customer's History</h1>
            </section>
            <section className="table_body">
                <table>
                    <thead>
                        <tr>
                            <th>Reference</th>
                            <th>Name</th>
                            <th>Location</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.length > 0 ? (
                            orders.map((order, i) => (
                                <tr key={i}>
                                    <td>TKN{i}</td>
                                    <td>
                                        {order.name}   
                                    </td>
                                    <td>{order.location}</td>
                                    <td>{order.ticketPrice}</td>
                                    <td>{order.quantity}</td>
                                    <td><strong>{order.total}</strong></td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={6}>Loading orders...</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </section>
        </main>
    );
}
