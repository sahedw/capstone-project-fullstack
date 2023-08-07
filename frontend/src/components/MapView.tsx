
import {FoodSpot} from "../types/FoodSpot.ts";
import {GoogleMap, MarkerF, useLoadScript} from "@react-google-maps/api";
import {useEffect, useState} from "react";
import {Position} from "../types/Position.ts";
import axios from "axios";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";

type Props = {
    apiKey: string,
    foodSpots: FoodSpot[]
}
function MapView({foodSpots, apiKey}: Props) {
    const [positions, setPositions] = useState<Position[]>()
    const [userCeter, setUserCenter] = useState<Position>()
    const allAddresses: string[] = [];

    foodSpots.forEach(foodSpot => allAddresses.push(convertGermanSpecialCharacters(foodSpot.address)))

    useEffect(() => {
        axios.post("/api/google/convert-address-multi", allAddresses)
            .then((response) => {
                setPositions(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])


   // const center = {lat: Number(position?[0].latitude), lng: Number(position?.longitude)};

    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>

    if (!positions) return <h1>Loading...</h1>


    return (
        <>
            <button onClick={() => console.log(positions)}></button>
            <GoogleMap
                zoom={10}
                center={{lat: 53.5488282, lng: 9.987170299999999}}
                mapContainerClassName={"google-map"}
            >
                {positions.map((location: Position) => {
                    return (
                        <MarkerF position={{lat: Number(location.latitude), lng: Number(location.longitude)}} key={location.latitude}/>
                    )
                })}

            </GoogleMap>
        </>
    );
}

export default MapView;