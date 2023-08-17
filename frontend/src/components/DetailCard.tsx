import {FoodSpot} from "../types/FoodSpot";
import DisplayPriceLevels from "../icons/DisplayPriceLevels";
import Instagram from "../icons/Instagram";


type Props = {
    foodSpot: FoodSpot,
    onEditMode: () => void
}

function DetailCard({foodSpot, onEditMode}: Props) {
    return (
        <>
            <p className={"detail-card-address"}>{foodSpot.address}</p>
            <section className={"detail-card-bottom"}>
                {foodSpot.instagramUsername !== "" ?
                    <a className={"link"} href={`https://www.instagram.com/${foodSpot.instagramUsername}/?hl=de`}>
                        <section className={"instagram-container"}>
                            <Instagram size={"1.5em"}/>
                            <p>{foodSpot.instagramUsername}</p>
                        </section>
                    </a>
                    :
                    <>
                    </>
                }
                <section>
                    <DisplayPriceLevels priceLevel={foodSpot.priceLevel} size={"2em"}/>
                </section>
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
