
import {FoodSpot} from "../types/FoodSpot.ts";
import {GoogleMap, MarkerF, useLoadScript} from "@react-google-maps/api";
import {useState} from "react";
import {Position} from "../types/Position.ts";

type Props = {
    foodSpots: FoodSpot[]
}
function MapView(props) {
    const [position, setPosition] = useState<Position>()


    const center = {lat: Number(position?.latitude), lng: Number(position?.longitude)};

    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>


    return (
        <>
            <GoogleMap
                zoom={12}
                center={center}
                mapContainerClassName={"google-map"}
            >
                <MarkerF position={center}/>
            </GoogleMap>
        </>
    );
}

export default MapView;