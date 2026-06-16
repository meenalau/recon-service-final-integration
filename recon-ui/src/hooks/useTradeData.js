
import { useEffect, useState } from "react";
import { getTrades, createTrade } from "../services/tradeService";

/**
 * useTradeData — central hook for all trade state.
 *
 * Returns:
 *   trades    — current list (newest first after addTrade)
 *   loading   — true while initial fetch is running
 *   error     — string if fetch failed, null otherwise
 *   addTrade  — async fn; POST a new trade and prepend to local state
 *   refresh   — re-fetch all trades from API
 */
export const useTradeData = () => {
  const [trades, setTrades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchTrades();
  }, []);

  const fetchTrades = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await getTrades();
      setTrades(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  /**
   * addTrade — calls createTrade (POST /api/v1/trades), then
   * prepends the returned trade object to local state so the table
   * updates immediately without a full re-fetch.
   * Throws on failure so TradeForm can catch and show error toast.
   */
  const addTrade = async (trade) => {
    const newTrade = await createTrade(trade);   // throws on non-2xx
      alert("adding new trade");
    setTrades((prev) => [newTrade, ...prev]);
    return newTrade;
  };

  return {
    trades,
    loading,
    error,
    addTrade,
    refresh: fetchTrades,
  };
};

export default useTradeData;