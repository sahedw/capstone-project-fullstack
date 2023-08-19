import {Category} from "../types/Category";
import {Link} from "react-router-dom";
import {useEffect} from "react";
import BurgerMenu from "./BurgerMenu";
import {Fade, Slide} from "react-awesome-reveal";
import CategoryCard from "./CategoryCard"


type Props = {
    onSignedIn: () => void,
    categories: Category[],
    onGetCategories: () => void
}

function HomePage({onSignedIn, categories}: Props) {
    useEffect(onSignedIn, [])

    return (
        <section className={"homepage-container"}>
            <BurgerMenu/>
            <section className={"homepage-header-container"}>
                <Slide>
                    <h2>Welcome back!</h2>
                </Slide>
                <Fade delay={1e3} cascade damping={1e-1}>
                    <p>Take a look at your saved Spots!</p>
                </Fade>
            </section>
            <section className={"category-grid-container"}>
                {categories?.map((category: Category) => {
                    return (
                        <Link className={"link"} to={`${category.name}`} key={category.name}>
                            <CategoryCard category={category.name}/>
                        </Link>
                    )
                })}
                <Link className={"link"} to={"/addCategory"}>
                    <section className={"for-add-category"}>
                        <img width={50} src="/add-category.png" alt=""/>
                    </section>
                </Link>
            </section>
        </section>
    );
}

export default HomePage;