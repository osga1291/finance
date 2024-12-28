import React from 'react';
import './cardComponent.css'; // Optional: for styling

function CardComponent({ title, data }) {
  return (
    <div className="card">
      <h2 className="card-title">{title}</h2>
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
            {data.slice(0,10).map((item, index) => (
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
  );
}

export default CardComponent;