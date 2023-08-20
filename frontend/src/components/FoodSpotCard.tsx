import {FoodSpot} from "../types/FoodSpot";
import {Link, Location, useLocation} from "react-router-dom";
import BackButton from "./BackButton";
import convertCategoryToHeaderFormat from "../utils/convertCategoryToHeaderFormat";
import {useState} from "react";
import ListView from "./ListView";
import MapView from "./MapView";
import BurgerMenu from "./BurgerMenu";
import toast, {Toaster} from "react-hot-toast";
import {ReactComponent as NotFoundAnimation} from "../animations/NotFoundAnimation/notFoundAnimation.svg";

type Props = {
    foodSpots: FoodSpot[]
}


function FoodSpotCard({foodSpots}: Props) {
    const [showMap, setShowMap] = useState(false)
    const location: Location = useLocation();
    const filteredFoodSpots: FoodSpot[] = foodSpots
        .filter((foodSpot) =>
            foodSpot.category === location.pathname.slice(1))


    if (filteredFoodSpots.length == 0) return (
        <section className={"fallback-loading-container yellow-background"}>
            <BackButton setClass={"normal"}/>
            <NotFoundAnimation/>
            <p>No spots saved.</p>
            <p>Go and add one <Link to={"/addFoodSpot"}>here</Link>.</p>
        </section>
    )

    if (showMap) {
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
        }, 3000);

    }


    return (
        <>
            <div><Toaster/></div>
            <section className={"overflow-menu"}>
                <BurgerMenu/>
                <BackButton setClass={"normal"}/>
                <section className={"category-foodspots-container"}>
                    <h1 className={"category-foodspots-header"}>All
                        your {convertCategoryToHeaderFormat(filteredFoodSpots[0].category) === "Doener" ? "Döner" : convertCategoryToHeaderFormat(filteredFoodSpots[0].category)} Spots:</h1>
                    <section className={"category-tabs-container"}>
                        <section className={"category-tab-container"}>
                            <button className={`category-tab ${showMap ? "tab-inactive" : ""}`}
                                    onClick={() => setShowMap(false)}>List
                            </button>
                            <button className={`category-tab ${showMap ? "" : "tab-inactive"}`}
                                    onClick={() => setShowMap(true)}>Map
                            </button>
                        </section>
                    </section>
                    {showMap ?
                        <MapView foodSpots={filteredFoodSpots}/>
                        :
                        <ListView foodSpots={filteredFoodSpots}/>
                    }
                </section>
            </section>
        </>
    );
}

export default FoodSpotCard;
