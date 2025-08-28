import { useEffect, useState } from "react";

export default function Dashboard({ token }) {
  const [user, setUser] = useState(null);
  const [planets, setPlanets] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Fetch user info
    fetch("http://localhost:8080/api/auth/me", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.json())
      .then(data => setUser(data))
      .catch(err => console.error(err));

    // Fetch horoscope/planets
    fetch("http://localhost:8080/api/horoscope/planets", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.json())
      .then(data => {
        setPlanets(data.output || []);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, [token]);

  if (loading) return <p>Loading your horoscope...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Hello, {user?.displayName || "User"}!</h1>

      <h2>Your Planet Positions</h2>
      <ul>
        {planets.map((p, idx) => (
          <li key={idx}>
            <strong>{p.planet.en}</strong>: {p.zodiac_sign.name.en}{" "}
            ({p.fullDegree.toFixed(2)}°) {p.isRetro === "True" && "(Retrograde)"}
          </li>
        ))}
      </ul>
    </div>
  );
}
