import React, { useEffect, useState } from "react";
import { getTrades } from "../services/tradeService";

export default function TradeTable({ refreshFlag }) {
  const [trades, setTrades] = useState([]);

  const loadTrades = async () => {
    const res = await getTrades();
    setTrades(res.data);
  };

  useEffect(() => {
    loadTrades();
  }, [refreshFlag]);

  return (

    <div className="table-container">

      <h3>Trades</h3>

      <table>

        <thead>
          <tr>
            <th>ID</th>
            <th>Trader</th>
            <th>Desk</th>
            <th>Instrument</th>
            <th>Counterparty</th>
            <th>Type</th>
            <th>Qty</th>
            <th>Price</th>
            <th>tradeReference</th>
            <th>Status</th>
             <th>TradeTimestamp</th>
          </tr>
        </thead>

        <tbody>
  {trades.map(trade => (
    <tr key={trade.tradeId}>
      <td>{trade.tradeId}</td>
      <td>{trade.traderName}</td>
      <td>{trade.tradingDesk}</td>
      <td>{trade.instrumentId}</td>
      <td>{trade.counterpartyId}</td>
      <td>{trade.tradeType}</td>
      <td>{trade.quantity}</td>
      <td>{trade.price}</td>
      <td>{trade.tradeReference}</td>
      <td>{trade.status}</td>
      <td>{trade.tradeTimestamp}</td>
    </tr>
  ))}
</tbody>

      </table>

    </div>
  )
}