import axios from "axios";

//const BASE_URL = "http://localhost:8080/api/v1";
//throught docker
const BASE_URL = "/api/v1";
export const createTrade = (trade) => {
  return axios.post(
    `${BASE_URL}/trades`,
    trade,
    {
      auth: {
        username: "admin",
        password: "admin123"
      }
    }
  );
};

export const getTrades = () => {
  return axios.get(
    `${BASE_URL}/trades`,
    {
      auth: {
        username: "admin",
        password: "admin123"
      }
    }
  );
};