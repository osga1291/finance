import React from 'react';
import { useNavigate  } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
    const navigate = useNavigate();
    const handleButtonClick = () => {
      navigate('/transaction'); // Replace with your target route
    };
    const handleButtonClickHome = () => {
      navigate('/'); // Replace with your target route
    }
    const handleButtonClickContact = () => {
      navigate('/trends'); // Replace with your target route
    }
  return (
    <nav className="navbar">
      <h1>Finance</h1>
      <ul>
        <li><a href="#home" onClick={handleButtonClickHome}>Home</a></li>
        <li><a href="#Transactions" onClick={handleButtonClick}>Transactions</a></li>
        <li><a href="#Trends" onClick={handleButtonClickContact}>Trends</a></li>
      </ul>
    </nav>
  );
}

export default Navbar;