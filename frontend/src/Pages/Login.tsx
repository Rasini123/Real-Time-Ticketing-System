import { useState } from 'react';
import axios from 'axios';
import "./Login.css";
import { useNavigate } from 'react-router-dom';

export default function Login() {  
    // State to hold the form data
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('client'); // Default role is 'client'

    const nav = useNavigate()


    // Handle form submission
    const handleSubmit = async (e: any) => {
        e.preventDefault();  // Prevent the default form submission


        try {
            // Send the data to the API
            const response = await axios.post('http://localhost:8080/api/new-acc', {
                email,
                password
            })

            if (!(response.data)){
                alert("Invalid password !")
                return
            }

            window.localStorage.setItem("user-id", response.data.userId)

            if (role === 'client'){
                nav("/client/events")
                window.localStorage.setItem("user-type", "CUSTOMER")
                window.location.reload()
            } else {
                nav("/vendor/events")
                window.localStorage.setItem("user-type", "VENDOR")
                window.location.reload()
            }

            alert("Logged in to the system")
            // You can redirect or show a success message here
        } catch (error) {
            // Handle error
            console.log(error)
        }
    };

    return(
        <div className="login-container">
            <h1>Log In Page</h1>
            <form className="login-form" onSubmit={handleSubmit}>
                <input 
                    type="email" 
                    placeholder="Enter Your Email" 
                    required 
                    className="input-field" 
                    value={email} 
                    onChange={(e) => setEmail(e.target.value)} 
                />
                <br />
                <input 
                    type="password" 
                    placeholder="Enter Your Password" 
                    required 
                    className="input-field" 
                    value={password} 
                    onChange={(e) => setPassword(e.target.value)} 
                />
                <br />
                
                {/* Client or Vendor selection */}
                <div className="select-container">
                    <label htmlFor="role-select">Select Role:</label>
                    <select 
                        id="role-select" 
                        className="role-select" 
                        value={role} 
                        onChange={(e) => setRole(e.target.value)}
                    >
                        <option value="client">Client</option>
                        <option value="vendor">Vendor</option>
                    </select>
                </div>
                
                <br />
                <input type="submit" value="Submit" className="submit-btn" />
            </form>
        </div>
    ); 
}
