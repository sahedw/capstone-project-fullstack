
import {FoodSpot} from "../types/FoodSpot.ts";
import {GoogleMap, MarkerF, useLoadScript} from "@react-google-maps/api";
import {FormEvent, useEffect, useState} from "react";
import {Position} from "../types/Position.ts";
import axios from "axios";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";

type Props = {
    apiKey: string,
    foodSpots: FoodSpot[]
}
function MapView({foodSpots, apiKey}: Props) {
    const [positions, setPositions] = useState<Position[]>()
    const [userCenter, setUserCenter] = useState<Position>()
    const [centerInput, setCenterInput] = useState<string>("")
    const [clickedMarker, setClickedMarker] = useState<FoodSpot>()
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

    useEffect(() => {
        axios.get("/api/user/city")
            .then((response) => {
                axios.post("/api/google/convert-address", `${response.data}`)
                    .then((response) => {
                        setUserCenter(response.data)
                    })
                    .catch(function (error) {
                        console.error(error);
                    });
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])

    function handleUserViewInput(e: FormEvent<HTMLFormElement>) {
        e.preventDefault()
        axios.post("/api/google/convert-address", `${centerInput}`)
            .then((response) => {
                setUserCenter(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    }

    function handleMarkerForFoodSpot(index: number) {
        const foundFoodSpot : FoodSpot | undefined = foodSpots.find((spot) => convertGermanSpecialCharacters(spot.address.toLowerCase().replace(/,/g, "")) === allAddresses[index])
        setClickedMarker(foundFoodSpot)
    }



   // const center = {lat: Number(position?[0].latitude), lng: Number(position?.longitude)};

    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>

    if (!positions) return <h1>Loading...</h1>

    if (!userCenter) return <h1>Loading...</h1>


    return (
        <>
            <form onSubmit={handleUserViewInput} className={"form-map-view"}>
                <input type="text" name={"input"} value={centerInput} onChange={(e) => {
                    setCenterInput(e.currentTarget.value)
                }}
                required={true}/>
                <button>View Location</button>
            </form>
            <section className={"marker-label-text"}>
                <p>{clickedMarker?.name}</p>
                <p>{clickedMarker?.address}</p>
            </section>
            <GoogleMap
                zoom={10}
                center={{lat: Number(userCenter?.latitude), lng: Number(userCenter?.longitude)}}
                mapContainerClassName={"google-map map-view"}
            >
                {positions.map((location: Position, index: number) => {
                    return (
                        <MarkerF onClick={() => handleMarkerForFoodSpot(index)} position={{lat: Number(location.latitude), lng: Number(location.longitude)}} key={location.latitude}/>
                    )
                })}

            </GoogleMap>
        </>
    );
}

export default MapView;