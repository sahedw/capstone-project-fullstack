import {FoodSpot} from "../types/FoodSpot.ts";
import GoogleMaps from "./GoogleMaps.tsx";

type Props = {
    foodSpot: FoodSpot,
    apiKey: string
}
function FoodSpotDetail({foodSpot, apiKey}: Props) {
    return (<>
            <div>{foodSpot.name}</div>
            <GoogleMaps address={foodSpot.address} apiKey={apiKey}/>
    </>
    );
}

export default FoodSpotDetail;