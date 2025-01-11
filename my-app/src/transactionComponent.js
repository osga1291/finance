import React, {useState, useEffect} from 'react';
import DatePicker from 'react-datepicker';
import './transactionComponent.css'; // Optional: for styling
import "react-datepicker/dist/react-datepicker.css";

function TransactionComponent({ initData }) {
  const [searchDateRange, setSearchDateRange] = useState([null, null]);
  const [startDate, endDate] = searchDateRange;
  const [searchDescription, setSearchDescription] = useState('');
  const [searchInstitution, setSearchInstitution] = useState('');
  const [searchType, setSearchType] = useState(null);
  const [data, setData] = useState(initData);
  const [offsetItems, setOffsetItems] = useState(10);
  const [loadMore, setLoadMore] = useState(false);
  const [done, setDone] = useState(false);

  useEffect(() => {
    setData(initData);
  }, [initData]);

  const handleSearchDateRange = (update) => {
    handleClearSearch();
    setSearchDateRange(update);
    handleSearchTypeChange('date');


  };
  const handleSearchDescriptionChange = (event) => {
    handleClearSearch();
    setSearchDescription(event.target.value);
  };

  const handleSearchInstitutionChange = (event) => {
    handleClearSearch();
    setSearchInstitution(event.target.value);
  };

  const handleSearchTypeChange = (type) => {
    setSearchType(type);
    setOffsetItems(0);
  };

  const handleSeeMore = () => {
    setLoadMore(true);
  };

  const handleClearSearch = () => {
    setSearchDateRange([null, null]);
    setSearchDescription('');
    setSearchInstitution('');
    setSearchType(null);
    setOffsetItems(10);
    setData([...initData]);
    setDone(false);
  };

  useEffect(() => {
    if (loadMore || offsetItems === 0) {
      const fetchMoreData = async () => {
        try {
          var response;
          if (searchType === null) {
            response = await fetch(`/api/transaction/all?offset=${offsetItems}&limit=20`); // Adjust endpoint as needed
          } else if (searchType === 'date' && startDate !== null && endDate !== null) {
            console.log(startDate, endDate);
            console.log('Search type:', searchType);
            const startDateFormatted = startDate;
            const endDateFormatted = endDate;
            response = await fetch(`/api/transaction?startDate=${formatDate(startDateFormatted)}&endDate=${formatDate(endDateFormatted)}&offset=${offsetItems}&limit=20`); // Adjust endpoint as needed
          } else if (searchDescription !== '' || searchInstitution !== '') {
            response = await fetch(`/api/transaction/field?key=${searchType}&value=${searchType === 'description' ? searchDescription : searchInstitution}&offset=${offsetItems}&limit=20`); // Adjust endpoint as needed
          }
          console.log('Response:', response);
          const result = await response.json();
          const formattedData = result.map(item => ({
            institution: item.properties[1].institution,
            amount: item.properties[2].amount,
            date: item.properties[0].date,
            description: item.properties[4].description
          }));
          console.log('Fetched data trans here:', formattedData);
          if (formattedData.length === 0) {
            setDone(true);
          }
          setData(prevData => offsetItems !== 0 ? [...prevData, ...formattedData] : [...formattedData]);
          setOffsetItems(prevOffsetItems => prevOffsetItems + 20);
          setLoadMore(false);

        } catch (error) {
          console.error('Error fetching more data:', error);
        }
      };
      fetchMoreData();
    }
  }, [loadMore, offsetItems, endDate, searchDescription, searchInstitution, searchType, startDate]);

  function formatDate(date) {
    console.log('Date:', date);
    return `${date.getMonth() + 1}/${date.getDate()}/${date.getFullYear()}`;
  }

  return (
    <main className="App-main">
    <div className="card">
      <h2 className="card-title">Transactions</h2>
      <DatePicker
        selectsRange
        startDate={startDate}
        endDate={endDate}
        onChange={handleSearchDateRange}
        placeholderText="Search by date"
        className="search-bar"
        dateFormat={"MM/dd/yyyy"}
      />
        <div className="search-fields">
          <input
            type="text"
            placeholder="Search by description"
            value={searchDescription}
            onChange={handleSearchDescriptionChange}
            onKeyDown={(e) => e.key === 'Enter' && handleSearchTypeChange('description')}
            className="search-bar"
          />
          <input
            type="text"
            placeholder="Search by institution"
            value={searchInstitution}
            onChange={handleSearchInstitutionChange}
            onKeyDown={(e) => e.key === 'Enter' && handleSearchTypeChange('institution')}
            className="search-bar"
          />
        </div>
        <button onClick={handleClearSearch} className="clear-search-button">Clear Search</button>
      <div className="card-content">
        <table className="card-table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Amount</th>
              <th>Description</th>
              <th>Institution</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item, index) => (
              <tr key={index} className="card-table-row">
                <td>{item.date}</td>
                <td>{item.amount}</td>
                <td>{item.description}</td>
                <td>{item.institution}</td>
              </tr>
            ))}
          </tbody>
        </table>
    </div>
    {!done && (
      <button onClick={handleSeeMore} className="see-more-button">
          See More
        </button>
    )}
    </div>
    </main>
  );
}

export default TransactionComponent;