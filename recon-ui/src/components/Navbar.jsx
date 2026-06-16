
import { Link } from "react-router-dom";

function Navbar() {

  return (
   <nav>
      <Link to="/">Trades</Link>
      {" | "}
      <Link to="/breaks">Breaks</Link>
    </nav>

  )
}

export default Navbar