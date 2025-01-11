import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement);

const ChartComponent = ({data}) => {
  console.log('Data:', data);
  const chartData = {
    labels: data.map(point => point.description),
    
    datasets: [
      {
        label: 'Balance',
        data: data.map(point => point.balance),
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(255, 159, 64, 0.2)',
          'rgba(255, 205, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(54, 162, 235, 0.2)',
        ],
        borderColor: [
          'rgb(255, 99, 132)',
          'rgb(255, 159, 64)',
          'rgb(255, 205, 86)',
          'rgb(75, 192, 192)',
          'rgb(54, 162, 235)',
        ],
        borderWidth: 1
      }]
  };

  console.log('Chart data:', chartData);

  const options = {
    scales: {
      x: {
        title: {
          display: true,
          text: 'Description'
        }
      },
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: 'Balance ($)'
        }
      },
    },
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: "400px", marginTop: '100px' }}>
      <Bar data={chartData} options={options} />
    </div>
  );
};

export default ChartComponent;