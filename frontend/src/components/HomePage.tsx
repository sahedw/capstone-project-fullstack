import {allCategories} from "../utils/allCategories.ts";
import Category from "./Category.tsx";
import {Link} from "react-router-dom";


function HomePage() {
    return (
        <>
            <section className={"category-grid-container"}>
                {allCategories.map((category: string, index: number) => {
                    return (
                        <Link className={"link"} to={`${category}`} key={index}>
                            <Category key={index} category={category}/>
                        </Link>
                    )
                })}
            </section>
            <Link to={"/addFoodSpot"}>
                <button className={"button-add"}>
                    <img className={"button-image-add"} src="/add-button.png" alt="plus icon"/>
                </button>
            </Link>

        </>
    );
}

export default HomePage;