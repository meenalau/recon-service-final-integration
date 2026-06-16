function TradeCard({ trade }) {

  return (

    <div className="detail-card">

      <h3>Trade Card</h3>

      <p>ID: {trade.tradeId}</p>
      <p>Trader: {trade.traderName}</p>
      <p>Desk: {trade.tradingDesk}</p>
      <p>instrumentId: {trade.instrumentId}</p>
      <p>counterpartyId: {trade.counterpartyId}</p>
      <p>Type: {trade.tradeType}</p>
      <p>Qty: {trade.quantity}</p>
      <p>Price: {trade.price}</p>
      <p>tradeReference: {trade.tradeReference}</p>      
      <p>Status: {trade.status}</p>      
      <p>tradeTimestamp: {trade.tradeTimestamp}</p>


    </div>
  )
}

export default TradeCard