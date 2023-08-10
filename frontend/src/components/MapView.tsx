import {FoodSpot} from "../types/FoodSpot.ts";
import {GoogleMap, MarkerF} from "@react-google-maps/api";
import {FormEvent, useEffect, useState} from "react";
import {Position} from "../types/Position.ts";
import axios from "axios";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";
import Swal from "sweetalert2";
import {renderToString} from "react-dom/server";
import DisplayPriceLevels from "../icons/DisplayPriceLevels.tsx";

type Props = {
    foodSpots: FoodSpot[]
}
function MapView({foodSpots}: Props) {
    const [positions, setPositions] = useState<Position[]>()
    const [userCenter, setUserCenter] = useState<Position>()
    const [centerInput, setCenterInput] = useState<string>("")
    const [userLocation, setUserLocation] = useState<Position>({
        latitude: "",
        longitude: "",
    });
    const allAddresses: string[] = [];

    foodSpots.forEach(foodSpot => allAddresses.push(convertGermanSpecialCharacters(foodSpot.address)))

    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const {latitude, longitude} = position.coords;
                    setUserLocation({latitude: latitude.toString(), longitude: longitude.toString()});
                },
                (error) => {
                    console.error(error);
                }
            );
        } else {
            console.error('Geolocation is not supported by this browser.');
        }
    }, []);

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
        return foodSpots.find((spot) => convertGermanSpecialCharacters(spot.address.toLowerCase().replace(/,/g, "")) === allAddresses[index])
    }

    const customMarkerIcon = {
        url: '/own-location.svg', // URL to your custom SVG marker
        scaledSize: new window.google.maps.Size(40, 40), // Adjust the size here
    };

   // const center = {lat: Number(position?[0].latitude), lng: Number(position?.longitude)};


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
            <GoogleMap
                zoom={10}
                center={{lat: Number(userCenter?.latitude), lng: Number(userCenter?.longitude)}}
                mapContainerClassName={"google-map map-view"}
            >
                {positions.map((location: Position, index: number) => {
                    const spot: FoodSpot | undefined = handleMarkerForFoodSpot(index)
                    const priceLevels = renderToString(<DisplayPriceLevels size={"1.5em"} priceLevel={spot?.priceLevel}/>)
                    return (
                        <MarkerF onClick={() => {
                            handleMarkerForFoodSpot(index)
                            Swal.fire({
                                title: `${spot?.name}`,
                                html: `${spot?.address}<br><br>${priceLevels}`,
                            })
                        }} position={{lat: Number(location.latitude), lng: Number(location.longitude)}} key={location.latitude+index}/>
                    )
                })}
                <MarkerF position={{lat: Number(userLocation?.latitude), lng: Number(userLocation?.longitude)}}
                         icon={customMarkerIcon}/>
            </GoogleMap>
        </>
    );
}

export default MapView;
