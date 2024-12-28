import React, {useState} from 'react';
import './transactionComponent.css'; // Optional: for styling

function TransactionComponent({ title, data }) {
  const [searchDate, setSearchDate] = useState('');
  const [searchDescription, setSearchDescription] = useState('');
  const [searchInstitution, setSearchInstitution] = useState('');

  const handleSearchDateChange = (event) => {
    setSearchDate(event.target.value);
  };

  const handleSearchDescriptionChange = (event) => {
    setSearchDescription(event.target.value);
  };

  const handleSearchInstitutionChange = (event) => {
    setSearchInstitution(event.target.value);
  };


  const filteredData = data.filter((item) =>
  item.date.includes(searchDate) &&
  item.description.toLowerCase().includes(searchDescription.toLowerCase()) &&
  item.institution.toLowerCase().includes(searchInstitution.toLowerCase())
);

  return (
    <main className="App-main">
    <div className="card">
      <h2 className="card-title">{title}</h2>
      <div className="search-fields">
          <input
            type="text"
            placeholder="Search by date"
            value={searchDate}
            onChange={handleSearchDateChange}
            className="search-bar"
          />
          <input
            type="text"
            placeholder="Search by description"
            value={searchDescription}
            onChange={handleSearchDescriptionChange}
            className="search-bar"
          />
          <input
            type="text"
            placeholder="Search by institution"
            value={searchInstitution}
            onChange={handleSearchInstitutionChange}
            className="search-bar"
          />
        </div>
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
            {filteredData.map((item, index) => (
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
    </div>
    </main>
  );
}

export default TransactionComponent;