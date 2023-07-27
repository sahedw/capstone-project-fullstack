import {allCategories} from "../utils/allCategories.ts";
import Category from "./Category.tsx";
import {Link} from "react-router-dom";

function HomePage() {
    return (
        <>
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