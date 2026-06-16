import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./styles/app.css";
import { BrowserRouter } from "react-router-dom";
import {   BreakProvider} from "./context/BreakContext";

import './index.css'
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
   <BrowserRouter>
    <BreakProvider>
        <App />
    </BreakProvider>
</BrowserRouter>
  </React.StrictMode>
);
