import React, { useState } from "react";
import TradeForm from "../components/TradeForm";
import TradeTable from "../components/TradeTable";

export default function TradesPage() {
  const [refreshFlag, setRefreshFlag] = useState(0);

  return (
      <div style={{ display: "flex", gap: "40px" }}>
    <TradeForm />
    <TradeTable />
  </div>
  );
}