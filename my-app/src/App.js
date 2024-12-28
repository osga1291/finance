import React, {use, useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './Navbar';
import Main from './Main';
import TransactionComponent from './transactionComponent';

function App() {
  const [data, setData] = useState([]);
  const [accountData, setAccountData] = useState([]);

  useEffect(() => {
    // Simulate fetching data
    const fetchData = async () => {
      try {
          const response = await fetch('/api/transaction/all'); // Replace with your endpoint
          const result = await response.json();
          console.log('Data result:', result);
          const formattedData = result.map(item => ({ institution: item.properties[1].institution, amount: item.properties[2].amount, date: item.properties[0].date, description: item.properties[3].description}));
          console.log('Data fetched:', formattedData);
          setData(formattedData);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      };
    fetchData();
  }, []);

  useEffect(() => {
    const fetchAccountData = async () => {
    try {
        const response = await fetch('/api/account/all'); // Replace with your endpoint
        const result = await response.json();
        console.log('Data result:', result);
        const formattedData = result.map(item => ({ institution: item.properties[1].institution, amount: item.properties[2].amount, date: item.properties[0].date, description: item.properties[3].description}));
        console.log('Data fetched:', formattedData);
        setAccountData(formattedData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }

    };
    fetchAccountData();
  }, []);
  return (
    <div className="App">
      <header className="App-header">
        <Router>
          <div>
            <Navbar />
            <Routes>
              <Route exact path="/transaction" element={<TransactionComponent data = {data} />} />
              <Route path="/" element={<Main data = {data, accountData } />} />
            </Routes>
          </div>
        </Router>
        </header>
    </div>
  );
}

export default App;
