import {FoodSpot} from "../types/FoodSpot.ts";
type Props = {
    foodSpot: FoodSpot
}


function FoodSpotCard({foodSpot}: Props) {


    return (
        <div className={"foodspot-card-container"}>
            <h3>{foodSpot.name}</h3>
            <img className={`card-image`} src={`${foodSpot.category}.png`} alt="food image"/>
            <p>{foodSpot.address}</p>
        </div>
    );
}

export default FoodSpotCard;
