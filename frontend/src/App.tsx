import './App.css'
import {useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";
import FoodSpotCard from "./components/FoodSpotCard.tsx";
import AddForm from "./components/AddForm.tsx";
import {allCategories} from "./utils/allCategories.ts";
import Category from "./components/Category.tsx";

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

    /*
    *         {foodSpots.map((foodSpot: FoodSpot) => {
            return (
                <FoodSpotCard key={foodSpot.id} foodSpot={foodSpot}/>
            )
        })}
    * */

  return (
    <>
            <section className={"category-grid-container"}>
                {allCategories.map((category: string, index: number) => {
                    return (
                        <Category key={index} category={category}/>
                    )
                })}
            </section>
            <button className={"button-add"}>
                <img className={"button-image-add"} src="/add-button.png" alt="plus icon"/>
            </button>
    </>
  )
}

export default App
