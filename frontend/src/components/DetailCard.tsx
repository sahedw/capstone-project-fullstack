
import {FoodSpot} from "../types/FoodSpot.ts";

type Props = {
    foodSpot: FoodSpot,
    apiKey: string,
    onEditMode: () => void
}

function DetailCard({foodSpot, onEditMode}: Props) {
    return (
        <>
            <section>
                <h1>{foodSpot.name}</h1>
                <p>{foodSpot.address}</p>
            </section>
            <section>
                <button className={"edit-button"} onClick={onEditMode}>
                    <img width={40} src="/edit.png" alt="left arrow"/>
                </button>
            </section>
        </>
    );
}

export default DetailCard;