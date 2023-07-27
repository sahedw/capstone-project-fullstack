import {FoodSpot} from "../types/FoodSpot.ts";
import {Link, Location, useLocation} from "react-router-dom";
import BackButton from "./BackButton.tsx";
import convertCategoryToHeaderFormat from "../utils/convertCategoryToHeaderFormat.ts";

type Props = {
    foodSpots: FoodSpot[]
}


function FoodSpotCard({foodSpots}: Props) {
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
                <section className={"category-card-grid"}>
                    {filteredFoodSpots.map((foodSpot: FoodSpot) => {
                        return (
                            <>
                                <Link to={`/${foodSpot.category}/${foodSpot.id}`} className={"link"} key={foodSpot.id}>
                                    <div className={"foodspot-card-container"}>
                                        <h3>{foodSpot.name}</h3>
                                        <img className={`card-image`} src={`${foodSpot.category}.png`}
                                             alt="food image"/>
                                        <p>{foodSpot.address}</p>
                                    </div>
                                </Link>
                            </>
                        )
                    })}
                </section>
            </section>
        </>
    );
}

export default FoodSpotCard;
