import {FoodSpot} from "../types/FoodSpot.ts";
import EditForm from "./EditForm.tsx";
import DetailCard from "./DetailCard.tsx";
import {useState} from "react";
import GoogleMaps from "./GoogleMaps.tsx";
import BackButton from "./BackButton.tsx";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId.ts";
import {Toaster} from "react-hot-toast";

type EditMode = () => void;

type Props = {
    foodSpot: FoodSpot,
    apiKey: string,
    onUpdate: (id: string, updatedFoodSpot: FoodSpotWithoutId, editMode: EditMode) => void,
    onDelete: (id: string) => void
}


function FoodSpotDetail({foodSpot, apiKey, onUpdate, onDelete}: Props) {
    const [editMode, setEditMode] = useState<boolean>(false)

    function handleEditMode() {
        setEditMode(!editMode)
    }

    return (<>
            <div><Toaster/></div>
            <section className={"foodspot-detail-container"}>
                <BackButton setClass={"normal"}/>
                {editMode ?
                    <>
                        <EditForm onDelete={onDelete} onEditMode={handleEditMode} onUpdate={onUpdate}
                                  foodSpot={foodSpot} apiKey={apiKey}/>
                    </>
                    :
                    <>
                        <h1>{foodSpot.name}</h1>
                        <GoogleMaps address={foodSpot.address} apiKey={apiKey}/>
                        <DetailCard foodSpot={foodSpot} apiKey={apiKey} onEditMode={handleEditMode}/>
                        <section className={"foodspot-detail-category"}>
                            <h3>{foodSpot.category === "DOENER" ? "DÖNER" : foodSpot.category}</h3>
                        </section>
                    </>}

            </section>
        </>
    );
}

export default FoodSpotDetail;