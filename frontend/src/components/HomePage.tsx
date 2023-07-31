import {allCategories} from "../utils/allCategories.ts";
import Category from "./Category.tsx";
import {Link} from "react-router-dom";
import {useEffect} from "react";

type Props = {
    onSignedIn: () => void,
    user?: string
}

function HomePage({onSignedIn, user}: Props) {
    useEffect(onSignedIn, [])
    return (
        <>
            <section>
                <h2>Wassup, {user}</h2>
                <p>Take a look at your saved FoodSpots!</p>
            </section>
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

        </>
    );
}

export default HomePage;