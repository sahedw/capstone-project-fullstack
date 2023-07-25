import './App.css'
import {useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";
import FoodSpotCard from "./components/FoodSpotCard.tsx";

function App() {
  const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);

  useEffect(() => {
    axios.get('/api/foodSpot')
        .then(response => {
          setFoodSpots(response.data);
        })
        .catch(function (error) {
          console.error(error);
        });
  }, [])

  return (
    <>
        {foodSpots.map((foodSpot) => {
            return (
                <FoodSpotCard key={foodSpot.id} foodSpot={foodSpot}/>
            )
        })}
    </>
  )
}

export default App
