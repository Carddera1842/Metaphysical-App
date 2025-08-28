import React from "react";
import Navbar from "../components/Navbar";

const Homepage = () => {
  return (
    <div>
      <Navbar />
      <main style={{ textAlign: "center", marginTop: "50px" }}>
        <h2>Welcome to MyAstroApp</h2>
        <p>Discover your horoscope and planetary positions every day!</p>
        <img 
          src="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba" 
          alt="astrology" 
          style={{ width: "50%", borderRadius: "10px", marginTop: "20px" }}
        />
      </main>
    </div>
  );
};

export default Homepage;
