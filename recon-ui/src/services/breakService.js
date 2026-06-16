import axios from "axios";

//const API_URL =  "http://localhost:8080/api/v1/recon/breaks";
const BASE_URL = "/api/v1";

export const fetchBreaks = async () => {

  const response =
      await axios.get(API_URL);

  return response.data;
};

export const resolveBreak = async (breakId) => {



  const response = await axios.put(
      `${API_URL}/${breakId}/resolve`,
      {},
      {
        auth: {
          username: "admin",
          password: "admin123"
        }
      }
  );

  return response.data;
};