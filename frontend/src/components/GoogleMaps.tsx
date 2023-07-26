import {GoogleMap, useLoadScript, Marker, MarkerF} from "@react-google-maps/api";
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



    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>


    return (
        <>
            <GoogleMap
                zoom={12}
                center={{lat: Number(position?.latitude), lng: Number(position?.longitude)}}
                mapContainerClassName={"google-map"}
            >
                <MarkerF position={{lat: Number(position?.latitude), lng: Number(position?.longitude)}}/>
            </GoogleMap>
        </>
    )

}

export default GoogleMaps;