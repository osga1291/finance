import React from 'react';
import { Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

// Register the components
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const TrendChart = ({ data }) => {

  console.log('trend data:', data);
  const formattedData1 = formatLine(data[0].amount);
  const formattedData2 = formatLine(data[1].amount);

  const chartData = {
    labels: formattedData1.date,
    datasets: [{
      label: data[0].date,
      data: formattedData1.val,
      fill: false,
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
      borderColor: 'rgba(75, 192, 192, 1)',
      borderWidth: 1
    },{
      label: data[0].date,
      data: formattedData2.val,
      fill: false,
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
      borderColor: 'rgba(75, 192, 192, 1)',
      borderWidth: 1
  }]
  };

  console.log('Chart data:', chartData);

function formatLine(data) {
    var amounts = [];
    var dates = [];
    for (var key of Object.keys(data)) {
      dates.push(key);
      amounts.push(data[key]);
    }
    return { date: dates, val: amounts };
  }

  const options = {
    scales: {
      x: {
        title: {
          display: true,
          text: 'Months'
        }
      },
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: 'Values'
        }
      },
    },
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '500px', marginTop: '100px' }}>
      <div style={{ width: '80%' }}>
        <Line data={chartData} options={options} />
      </div>
    </div>
  );
};

export default TrendChart;