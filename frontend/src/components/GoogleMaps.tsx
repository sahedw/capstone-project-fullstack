import {GoogleMap, MarkerF} from "@react-google-maps/api";
import {Position} from "../types/Position.ts";
import axios from "axios";
import {useEffect, useState} from "react";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";

type Props = {
    address: string
}

function GoogleMaps({address}: Props) {
    const [position, setPosition] = useState<Position>()

    useEffect(() => {
        const delay = 1000;
        const timeoutId = setTimeout(() => {
            axios.post("/api/google/convert-address", `${convertGermanSpecialCharacters(address)}`)
                .then((response) => {
                    setPosition(response.data);
                })
                .catch(function (error) {
                    console.error(error);
                });
        }, delay);
        return () => clearTimeout(timeoutId);
    }, [address]);

    const center = {lat: Number(position?.latitude), lng: Number(position?.longitude)};


    if (!position) return (
        <section className={"placeholder-detail-map"}>
            <p>Loading...</p>
        </section>
    )

    if (!position || isNaN(Number(position.latitude)) || isNaN(Number(position.longitude))) {
        return <h1>Invalid address. Please check the address and update it.</h1>;
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