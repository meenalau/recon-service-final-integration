//function BreakTable({ breaks }) { before Resolve button working -just display button - no action 

// passing parameter onReolve now to make it functional 
function BreakTable({ breaks, onResolve }) {
  return (
    <table border="1" width="100%">
      <thead>
        <tr>
          <th>Break ID</th>
          <th>Trade ID</th>
          <th>Break Date</th>
          <th>Break Type</th>
          <th>Our Qty</th>
          <th>Their Qty</th>
          <th>Our Amount</th>
          <th>Their Amount</th>
          <th>Currency</th>
          <th>Status</th>
          <th>Resolved By</th>
          <th>Action</th>
        </tr>
      </thead>

      <tbody>
        {breaks.map((brk) => (
          <tr key={brk.breakId}>
            <td>{brk.breakId}</td>
            <td>{brk.tradeId}</td>
            <td>{brk.breakDate}</td>
            <td>{brk.breakType}</td>
            <td>{brk.ourQty}</td>
            <td>{brk.theirQty}</td>
            <td>{brk.ourAmount}</td>
            <td>{brk.theirAmount}</td>
            <td>{brk.currency}</td>
            <td>{brk.status}</td>
            <td>{brk.resolvedBy || "-"}</td>

            {/* before resolve button working -just display button 
            
            <td>
              {brk.status !== "RESOLVED" && (<button onClick={() => console.log(brk.breakId)}    >    Resolve    </button>)}

            </td> */}
            <td>
              <button onClick={() => onResolve(brk)}>    Resolve</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default BreakTable;