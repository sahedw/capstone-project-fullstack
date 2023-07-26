import {GoogleMap, useLoadScript, MarkerF} from "@react-google-maps/api";
import {Position} from "../types/Position.ts";
import axios from "axios";
import {useEffect, useState} from "react";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";

type Props = {
    apiKey: string,
    address: string
}

function GoogleMaps({apiKey, address}: Props) {
    const [position, setPosition] = useState<Position>()

    useEffect(() => {
        axios.post("/api/google/convert-address", `${convertGermanSpecialCharacters(address)}`)
            .then((response) => {
                setPosition(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    },[])

    const center = {lat: Number(position?.latitude), lng: Number(position?.longitude)};

    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>

    if (!position || isNaN(Number(position.latitude)) || isNaN(Number(position.longitude))) {
        return <h1>Invalid position data. Please check the address.</h1>;
    }

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
    )

}

export default GoogleMaps;