import { BrowserRouter, Routes, Route } from "react-router-dom";
import TradesPage from "./pages/TradesPage";
import BreaksPage from "./pages/BreaksPage";
import Navbar from "./components/Navbar";

function App() {
  return (
    
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<TradesPage />} />
        <Route path="/breaks" element={<BreaksPage />} />
      </Routes>
    </>
    
  );
}

export default App;