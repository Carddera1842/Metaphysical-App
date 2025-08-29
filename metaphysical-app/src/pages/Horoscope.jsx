import { useState, useEffect } from "react";

export default function Horoscope() {
  const [formData, setFormData] = useState({
    year: "",
    month: "",
    date: "",
    hours: "",
    minutes: "",
    seconds: "0",
  });

  const [location, setLocation] = useState({ latitude: 0, longitude: 0, timezone: 0 });
  const [planets, setPlanets] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const timezoneOffset = new Date().getTimezoneOffset() / -60; 
          setLocation({
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
            timezone: timezoneOffset,
          });
        },
        (err) => console.warn("Geolocation error:", err)
      );
    }
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const res = await fetch("http://localhost:8080/api/horoscope/planets", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({
          year: Number(formData.year),
          month: Number(formData.month),
          date: Number(formData.date),
          hours: Number(formData.hours),
          minutes: Number(formData.minutes),
          seconds: Number(formData.seconds),
          latitude: location.latitude,
          longitude: location.longitude,
          timezone: location.timezone,
          config: {
            observation_point: "topocentric",
            ayanamsha: "tropical",
            language: "en",
          },
        }),
      });

      if (!res.ok) throw new Error(`Failed to fetch horoscope: ${res.status}`);
      const data = await res.json();
      setPlanets(data.output);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Horoscope Planets</h1>

      <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
        <div>
          <label>Year: </label>
          <input type="number" name="year" value={formData.year} onChange={handleChange} required />
        </div>
        <div>
          <label>Month: </label>
          <input type="number" name="month" value={formData.month} onChange={handleChange} required />
        </div>
        <div>
          <label>Date: </label>
          <input type="number" name="date" value={formData.date} onChange={handleChange} required />
        </div>
        <div>
          <label>Hours: </label>
          <input type="number" name="hours" value={formData.hours} onChange={handleChange} required />
        </div>
        <div>
          <label>Minutes: </label>
          <input type="number" name="minutes" value={formData.minutes} onChange={handleChange} required />
        </div>
        <button type="submit">Get Horoscope</button>
      </form>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {planets.length > 0 && (
        <ul>
          {planets.map((planet, index) => (
            <li key={index}>
              <strong>{planet.planet.en}</strong>: {planet.zodiac_sign.name.en}{" "}
              ({planet.isRetro === "True" ? "Retrograde" : "Direct"})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
