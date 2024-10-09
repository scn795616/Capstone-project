// src/services/api.js
import axios from "axios";

// Create a new instance of axios
const api = axios.create({
    baseURL: "http://localhost:8989/api", // Your Spring Boot backend base URL
    headers: {
        "Content-Type": "application/json",
    },
});



export default api; // Export the configured axios instance for use in other parts of the app
