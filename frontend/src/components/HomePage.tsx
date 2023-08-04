import {allCategories} from "../utils/allCategories.ts";
import Category from "./Category.tsx";
import {Link} from "react-router-dom";
import {useEffect} from "react";

type Props = {
    onSignedIn: () => void,
    onLogout: () => void,
    user?: string
}

function HomePage({onSignedIn, onLogout, user}: Props) {
    useEffect(onSignedIn, [])
    return (
        <section className={"homepage-container"}>
            <section className={"homepage-header-container"}>
                <h2>Wassup, {user}</h2>
                <p>Take a look at your saved FoodSpots!</p>
            </section>
            <button className={"logout-button"} onClick={onLogout}>{user !== undefined && "Logout"}</button>
            <section className={"category-grid-container"}>
                {allCategories.map((category: string) => {
                    return (
                        <Link className={"link"} to={`${category}`} key={category}>
                            <Category category={category}/>
                        </Link>
                    )
                })}
            </section>
            <Link to={"/addFoodSpot"}>
                <button className={"button-add"}>
                    <img width={40} src="/plus.png" alt="plus icon"/>
                </button>
            </Link>

        </section>
    );
}

export default HomePage;