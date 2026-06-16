import React from "react";
import { useForm } from "react-hook-form";
import { createTrade } from "../services/tradeService";
import { toast } from "react-toastify";

export default function TradeForm({ onTradeAdded }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();

  const onSubmit = async (data) => {
    try {
      alert("on submit called ");
      console.log("test 1  : FORM VALUES:", data); 
      await createTrade(data);
      toast.success("Trade created successfully");
      reset();
      onTradeAdded?.();
    } catch (err) {
      toast.error(err?.response?.data?.message || "Trade failed");
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} style={styles.form}>
      <h3>Trade Entry</h3>

      {/* TRADER NAME */}
      <input
        placeholder="Trader Name"
        {...register("traderName", { required: "Required" })}
      />

      {/* TRADING DESK */}
      <input
        placeholder="Trading Desk"
        {...register("tradingDesk", { required: "Required" })}
      />

      {/* INSTRUMENT ID (number) */}
      <input
        type="number"
        placeholder="Instrument ID"
        {...register("instrumentId", {
          required: "Required",
          valueAsNumber: true,
        })}
      />

      {/* COUNTERPARTY ID */}
      <input
        type="number"
        placeholder="Counterparty ID"
        {...register("counterpartyId", {
          required: "Required",
          valueAsNumber: true,
        })}
      />

      {/* TRADE TYPE */}
      <select {...register("tradeType", { required: "Required" })}>
        <option value="">Select Trade Type</option>
        <option value="BUY">BUY</option>
        <option value="SELL">SELL</option>
      </select>

  <input
  type="number"
  placeholder="Quantity"
  {...register("quantity", {
    required: "Quantity is required",
    valueAsNumber: true,
    validate: value =>
      value > 0 || "Quantity must be greater than 0"
  })}
/>

{errors.quantity && (
  <p style={{ color: "red", margin: "2px 0", fontSize: "12px" }}>
    {errors.quantity.message}
  </p>
)}

      {/* PRICE */}
      <input
        type="number"
        step="0.01"
        placeholder="Price"
        {...register("price", {
          required: "Required",
          valueAsNumber: true,
          validate: (v) => v > 0 || "Must be > 0",
        })}
      />

      {/* TRADE REFERENCE */}
      <input
  placeholder="Trade Reference"
  {...register("tradeReference", {
    required: "Trade Reference is required",
    pattern: {
      value: /^TRD\d{7}$/,
      message: "Format must be TRD followed by 7 digits (e.g. TRD2026012)"
    }
  })}
/>

<p className="error">
  {errors.tradeReference?.message}
</p>

      {/* STATUS */}
      <select {...register("status", { required: "Required" })}>
        <option value="">Select Status</option>        
        <option value="EXECUTED">EXECUTED</option>
        <option value="CONFIRMED">CONFIRMED</option>
         <option value="MATCHED">MATCHED</option>       
          <option value="SETTLED">SETTLED</option>
             <option value="CUSTODY">CUSTODY</option>
      </select>

      {/* TRADE TIMESTAMP */}
<input
  type="datetime-local"  {...register("tradeTimestamp", { required: "Required" })}
/>

      <button type="submit">Submit Trade</button>
    </form>
  );
}

const styles = {
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
    width: "350px",
  },
};