import {FoodSpot} from "../types/FoodSpot.ts";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {Position} from "../types/Position.ts";
import {GoogleMap, MarkerF} from "@react-google-maps/api";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters.ts";
import BurgerMenu from "./BurgerMenu.tsx";
import Swal from 'sweetalert2'
import DisplayPriceLevels from "../icons/DisplayPriceLevels.tsx";
import {renderToString} from "react-dom/server";
import {ReactComponent as MapLoadingAnimation} from "../animations/LoadingMapAnimation/loadingMapAnimation.svg";




import toast, {Toaster} from "react-hot-toast";

type Props = {
    foodSpots: FoodSpot[]
}

function MapOverview({foodSpots}: Props) {
    const [userCenter, setUserCenter] = useState<Position>()
    const [positions, setPositions] = useState<Position[]>()
    const [centerInput, setCenterInput] = useState<string>("")
    const [userLocation, setUserLocation] = useState<Position>({
        latitude: "",
        longitude: "",
    });

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
            setTimeout(() => {
                toast("You're current position is set", {
                    duration: 2000,
                    icon: '📍',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "#f3d935"

                    }
                })
            }, 2000);
        } else {
            console.error('Geolocation is not supported by this browser.');
        }
    }, []);

    const allStreets: string[] = [];

    foodSpots.forEach(foodSpot => allStreets.push(convertGermanSpecialCharacters(foodSpot.address, true)))

    useEffect(() => {
        axios.post("/api/google/convert-address-multi", allStreets)
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

    if (!positions) return (<section className={"fallback-loading-container yellow-background"}>
        <MapLoadingAnimation/>
        <h2>Loading your Spots...</h2>
    </section>)

    if (!userCenter) return (<section className={"fallback-loading-container"}>

    </section>)

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
        return foodSpots.find((spot) => convertGermanSpecialCharacters(spot.address.replace(/,/g, ""), true) === allStreets[index]);
    }

    const customMarkerIcon = {
        url: '/own-location.png', // URL to your custom SVG marker
        scaledSize: new window.google.maps.Size(40, 40), // Adjust the size here
    };


    return (
        <>
            <div><Toaster/></div>
            <section className={"overflow-menu"}>
                <BurgerMenu/>
                <section className={"map-page-container"}>
                    <form onSubmit={handleUserViewInput} className={"form-map-view"}>
                        <input className={"input-change-location"} type="text" name={"input"} value={centerInput} onChange={(e) => {
                            setCenterInput(e.currentTarget.value)
                        }}
                               required={true}/>
                        <button className={"button-change-location"}>View Location</button>
                    </form>
                    <GoogleMap
                        zoom={10}
                        center={{lat: Number(userCenter?.latitude), lng: Number(userCenter?.longitude)}}
                        mapContainerClassName={"google-map map-overview"}
                    >
                        {positions.map((location: Position, index: number) => {
                            const spot: FoodSpot | undefined = handleMarkerForFoodSpot(index)
                            const priceLevels = renderToString(<DisplayPriceLevels size={"1.5em"} priceLevel={spot?.priceLevel}/>)

                            return (
                                <MarkerF onClick={() => {
                                    Swal.fire({
                                        title: `${spot?.name}`,
                                        html: `${spot?.address}<br><br>${priceLevels}`,
                                    })
                                }}
                                         position={{lat: Number(location.latitude), lng: Number(location.longitude)}}
                                         key={spot?.id}/>
                            )
                        })}
                        <MarkerF onClick={() => {
                            Swal.fire({
                                title: `Hm?`,
                                html: "That's your position"
                            })
                        }} position={{lat: Number(userLocation?.latitude), lng: Number(userLocation?.longitude)}}
                                 icon={customMarkerIcon}/>
                    </GoogleMap>
                </section>
            </section>

        </>
    );
}

export default MapOverview;
