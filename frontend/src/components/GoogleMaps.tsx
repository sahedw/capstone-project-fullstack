import {GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";

type Props = {
    apiKey: string
}
function GoogleMaps({apiKey}: Props) {

    const {isLoaded} = useLoadScript({googleMapsApiKey: apiKey})

    if (!isLoaded) return <h1>Loading...</h1>

    return <Map/>;
}

function Map() {
    return <GoogleMap
        zoom={10}
        center={{lat: 53.55, lng: 9.99}}
        mapContainerClassName={"google-map"}
    >

    </GoogleMap>
}

export default GoogleMaps;