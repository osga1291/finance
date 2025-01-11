import React, {use, useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './Navbar';
import Main from './Main';
import TransactionComponent from './transactionComponent';
import TrendChart from './TrendChart';

function App() {
  const [data, setData] = useState([]);
  const [accountData, setAccountData] = useState([]);
  const [timelineData, setTimelineData] = useState([]);

  useEffect(() => {
    // Simulate fetching data
    const fetchData = async () => {
      try {
          const response = await fetch('/api/transaction/all?offset=0&limit=10'); // Replace with your endpoint
          const result = await response.json();
          console.log('Trans result:', result);
          const formattedData = result.map(item => ({ institution: item.properties[1].institution, amount: item.properties[2].amount, date: item.properties[0].date, description: item.properties[4].description}));
          console.log('Trans fetched:', formattedData);
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
        const response = await fetch('/api/account/all?offset=0&limit=10'); // Replace with your endpoint
        const result = await response.json();
        console.log('Account result:', result);
        const formattedData = result.map(item => ({ institution: item.properties[0].institution, balance: item.properties[3].balance, accountClass: item.properties[2].accountClass, description: item.properties[4].description}));
        console.log('Data fetched:', formattedData);
        setAccountData(formattedData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }

    };
    fetchAccountData();
  }, []);

  useEffect(() => {
    const fetchTimelineData = async () => {
      try {
          const response = await fetch('/api/timeline?timeChunk=30d&offset=0&limit=100'); // Replace with your endpoint
          const result = await response.json();
          const formattedData = Object.entries(result).map(([key, val]) => ({ date: key, amount: val }));
          console.log(typeof result);
          console.log('Timeline result:', formattedData);
          setTimelineData(formattedData);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
  
      };
      fetchTimelineData();
    }, []);
  

  return (
    <div className="App">
      <header className="App-header">
        <Router>
          <div>
            <Navbar />
            <Routes>
              <Route exact path="/transaction" element={<TransactionComponent initData={data} />} />
              <Route path="/" element={<Main data = {data} accountData={accountData} />} />
              <Route path="/trends" element={<TrendChart data={timelineData}/>} />
            </Routes>
          </div>
        </Router>
        </header>
    </div>
  );
}

export default App;
