import {FoodSpot} from "../types/FoodSpot.ts";
type Props = {
    foodSpot: FoodSpot
}


function FoodSpotCard(props: Props) {


    return (
        <div className={"foodspot-card-container"}>
            <h3>{props.foodSpot.name}</h3>
            <img className={`card-image`} src={`${props.foodSpot.category}.png`} alt="food image"/>
            <p>{props.foodSpot.address}</p>
        </div>
    );
}

export default FoodSpotCard;
