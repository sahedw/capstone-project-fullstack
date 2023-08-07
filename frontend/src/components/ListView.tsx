import {FoodSpot} from "../types/FoodSpot.ts";
import {Link} from "react-router-dom";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";

type Props = {
    foodSpots: FoodSpot[]
}
function ListView({foodSpots}: Props) {
    return (
        <section className={"category-card-grid"}>
            {foodSpots.map((foodSpot: FoodSpot) => {
                return (
                    <Link to={`/${foodSpot.category}/${foodSpot.id}`} className={"link"} key={foodSpot.id}>
                        <div className={"foodspot-card-container"} key={foodSpot.id}>
                            <h3>{foodSpot.name}</h3>
                            <img className={`card-image ${convertGermanSpecialCharacters(foodSpot.category)}`} src={`${foodSpot.category}.png`}
                                 alt="food image"/>
                        </div>
                    </Link>
                )
            })}
        </section>
    );
}

export default ListView;