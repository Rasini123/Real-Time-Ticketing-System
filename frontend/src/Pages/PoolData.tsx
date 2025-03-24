import React, { useEffect, useState } from "react";
import axios from "axios";
import "./PoolData.css";
import { useParams } from "react-router-dom";

interface PoolData {
    count: number;
    remaining: number;
}

export default function PoolData() {
    const [poolData, setPoolData] = useState<PoolData | null>(null);
    const { id } = useParams()

    // Function to fetch pool data
    const fetchPoolData = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/vendor/pool-data/" + id); // Update the API endpoint
            
            console.log(response.data)
            setPoolData(response.data);
            console.log(response.data)
        } catch (error) {
            console.error("Error fetching pool data:", error);
        }
    };

    // Use periodic polling to update pool data
    useEffect(() => {
        fetchPoolData(); // Initial fetch

        const intervalId = setInterval(() => {
            fetchPoolData(); // Fetch data every 10 seconds
        }, 10000);

        return () => clearInterval(intervalId); // Cleanup interval on component unmount
    }, []);

    const terminate =  async () => {
        const res = await axios.get("http://localhost:8080/api/vendor/stop/" + id)
        console.log(res)
    }

    return (
        <div className="pool-data-container">
            <h2>Pool Data</h2>
            {poolData ? (
                <div className="pool-data-display">
                    <p><strong>Pool Capacity:</strong> {poolData.count}</p>
                    <p><strong>Ticket Count Remaining:</strong> {poolData.remaining}</p>
                    <button onClick={terminate}>Terminate Event</button>
                </div>
            ) : (
                <p>Loading pool data...</p>
            )}
        </div>
    );
}
