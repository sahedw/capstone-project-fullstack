import './App.css'
import {useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";
import {allCategories} from "./utils/allCategories.ts";
import {Route, Routes} from "react-router-dom";
import HomePage from "./components/HomePage.tsx";
import FoodSpotCard from "./components/FoodSpotCard.tsx";
import AddForm from "./components/AddForm.tsx";
import {DtoFoodSpot} from "./types/DtoFoodSpot.ts";

function App() {
    const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);

    useEffect((): void => {
        axios.get('/api/foodSpot')
            .then(response => {
                setFoodSpots(response.data);
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])

    function getAllFoodSpots(): void {
        axios.get('/api/foodSpot')
            .then(response => {
                setFoodSpots(response.data);
            })
            .catch(function (error) {
                console.error(error);
            });
    }

    function handleAddFoodSpot(newFoodSpot: DtoFoodSpot): void {
        axios.post("/api/foodSpot", newFoodSpot)
            .then(() => getAllFoodSpots())
            .catch(function (error) {
                console.error(error);
            });
    }



    return (
        <>
            <Routes>
                <Route path={"/"}
                       element={<HomePage/>}>
                </Route>
                <Route path={"/addFoodSpot"}
                       element={<AddForm onAdd={handleAddFoodSpot}/>}>
                </Route>
            {allCategories.map((category: string, index: number) => {
                return (
                        <Route path={`/${category}`} key={index}
                               element={<FoodSpotCard foodSpots={foodSpots} />}>
                        </Route>
                )
            })}
            </Routes>
        </>
    )
}

export default App
