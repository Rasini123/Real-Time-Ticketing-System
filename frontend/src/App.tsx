import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css'
import Login from "./Pages/Login";
import EventsPage from "./Pages/EventsPage";
import History from "./Pages/History";
import NewEventPage from "./Pages/NewEventPage";
import VendorHistory from "./Pages/VendorHistory";
import { useEffect, useState } from "react";
import PoolData from "./Pages/PoolData";
import CustomerEventsPage from "./Pages/CustomerEventsPage";

function App() {
  const [vendor, setVendor] = useState<boolean>(false);
  const [customer, setCustomer] = useState<boolean>(false);

  useEffect(() => {
    if (window.localStorage.getItem("user-type") === "CUSTOMER"){
      setCustomer(true)
    }

    if (window.localStorage.getItem("user-type") === "VENDOR"){
      setVendor(true)
    }
  }, [])

  const logout = () => {
    window.localStorage.removeItem("user-type")
    window.location.reload()
  }

  return (
    <>
      <BrowserRouter>
      {customer &&
          <header>
          <nav className="navbar">
              <div className="logo">
                  <a href="#">RealTimeTicketing</a>
              </div>
              <ul className="nav-links">
                  <li><a href="/client/events">Events</a></li>
                  <li><a href="/client/history">History</a></li>
              </ul>

              <a onClick={logout} href="/" className="logout">Logout</a>
              <div className="menu-toggle" id="mobile-menu">
                  <span className="bar"></span>
                  <span className="bar"></span>
                  <span className="bar"></span>
              </div>
          </nav>
      </header>
      }
      
      {vendor && 
          <header>
        <nav className="navbar">
            <div className="logo">
              <a href="#">RealTimeTicketing</a>
            </div>
            <ul className="nav-links">
                <li><a href="/vendor/newevent">New Event</a></li>
                <li><a href="/vendor/events">Active Events</a></li>
                <li><a href="/vendor/history">Terminated Events</a></li>
            </ul>

            <a onClick={logout} href="/" className="logout">Logout</a>
            <div className="menu-toggle" id="mobile-menu">
                <span className="bar"></span>
                <span className="bar"></span>
                <span className="bar"></span>
            </div>
        </nav>
    </header>}

      <Routes>
          <Route path="client/events" element={<CustomerEventsPage/>} />
          <Route path="client/history" element={<History/>} />
          
          <Route path="*" element={<Login/>} />

          <Route path="vendor/events" element={<EventsPage/>} />
          <Route path="vendor/events/:id" element={<PoolData/>}/>
          <Route path="vendor/newevent" element={<NewEventPage/>} />
          <Route path="vendor/history" element={<VendorHistory/>} />        
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
