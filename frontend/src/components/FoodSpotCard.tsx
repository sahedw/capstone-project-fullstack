import {FoodSpot} from "../types/FoodSpot.ts";
import {Location, useLocation} from "react-router-dom";
import BackButton from "./BackButton.tsx";
import convertCategoryToHeaderFormat from "../utils/convertCategoryToHeaderFormat.ts";
import {useState} from "react";
import ListView from "./ListView.tsx";
import MapView from "./MapView.tsx";

type Props = {
    foodSpots: FoodSpot[]
}


function FoodSpotCard({foodSpots}: Props) {
    const [showMap, setShowMap] = useState(false)
    const location: Location = useLocation();
    const filteredFoodSpots: FoodSpot[] = foodSpots
        .filter((foodSpot) =>
            foodSpot.category === location.pathname.slice(1))


    if (filteredFoodSpots.length == 0) return <h1>No saved FoodSpots</h1>


    return (
        <>
            <BackButton setClass={"normal"}/>
            <section className={"category-foodspots-container"}>
                <h1 className={"category-foodspots-header"}>All
                    your {convertCategoryToHeaderFormat(filteredFoodSpots[0].category) === "Doener" ? "Döner" : convertCategoryToHeaderFormat(filteredFoodSpots[0].category)} FoodSpots:</h1>
                <section className={"category-tabs-container"}>
                    <section className={"category-tab-container"}>
                        <button className={`category-tab ${showMap ? "tab-inactive" : ""}`}
                                onClick={() => setShowMap(false)}>List
                        </button>
                        <button className={`category-tab ${showMap ? "" : "tab-inactive"}`}
                                onClick={() => setShowMap(true)}>Map
                        </button>
                    </section>
                </section>
                {showMap ?
                    <MapView/>
                    :
                    <ListView foodSpots={filteredFoodSpots}/>
                }
            </section>
        </>
    );
}

export default FoodSpotCard;
