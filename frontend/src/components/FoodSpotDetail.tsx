import {FoodSpot} from "../types/FoodSpot.ts";
import GoogleMaps from "./GoogleMaps.tsx";

type Props = {
    foodSpot: FoodSpot,
    apiKey: string
}
function FoodSpotDetail({foodSpot, apiKey}: Props) {
    return (<>
            <section className={"foodspot-detail-container"}>
                <section>
                    <h1>{foodSpot.name}</h1>
                    <p>{foodSpot.address}</p>
                </section>
                <GoogleMaps address={foodSpot.address} apiKey={apiKey}/>
                <section className={"foodspot-detail-category"}>
                    <h3>{foodSpot.category === "DOENER" ? "DÖNER" : foodSpot.category}</h3>
                </section>
            </section>

    </>
    );
}

export default FoodSpotDetail;