import './App.css'
import {useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";
import {allCategories} from "./utils/allCategories.ts";
import {Route, Routes} from "react-router-dom";
import HomePage from "./components/HomePage.tsx";
import FoodSpotCard from "./components/FoodSpotCard.tsx";
import AddForm from "./components/AddForm.tsx";
import {FoodSpotWithoutId} from "./types/FoodSpotWithoutId.ts";
import FoodSpotDetail from "./components/FoodSpotDetail.tsx";


function App() {
    const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);
    const [apiKey, setApiKey] = useState<string>("");

    useEffect(() => {
        axios.get('/api/google/key')
            .then(response => {
                setApiKey(response.data);
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])

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

    function handleAddFoodSpot(newFoodSpot: FoodSpotWithoutId): void {
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
                    {allCategories.map((category: string) => {
                        const filteredByCurrentCategory: FoodSpot[] = foodSpots.filter((spot: FoodSpot) => spot.category == category)
                        return (<>
                                <Route path={`/${category}`} key={category}
                                       element={<FoodSpotCard foodSpots={foodSpots}/>}>
                                </Route>
                                {filteredByCurrentCategory.map((foodSpot: FoodSpot) => {
                                    return (
                                        <Route path={`/${category}/${foodSpot.id}`} key={category+foodSpot.id}
                                               element={<FoodSpotDetail apiKey={apiKey} foodSpot={foodSpot}/>}>
                                        </Route>
                                    )
                                })}
                            </>
                        )
                    })}
                </Routes>
        </>
    )
}

export default App
