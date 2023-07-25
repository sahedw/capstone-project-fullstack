import './App.css'
import {useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";

function App() {
  const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);

  useEffect(() => {
    axios.get('/api/quiz')
        .then(response => {
          setFoodSpots(response.data);
        })
        .catch(function (error) {
          console.error(error);
        });
  }, [])

  return (
    <>
      <FoodSpotCard/>
    </>
  )
}

export default App
