import React from 'react';
import ChartComponent from './ChartComponent';
import CardComponent from './cardComponent';

function Main({data, accountData}) {
  return (
    <main className="App-main">
      <ChartComponent data={accountData} />
      <CardComponent title="Transactions" data={data}/>
    </main>
  );
}

export default Main;