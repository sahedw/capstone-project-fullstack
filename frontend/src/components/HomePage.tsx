import {allCategories} from "../utils/allCategories";
import Category from "./Category";
import {Link} from "react-router-dom";
import {useEffect} from "react";
import BurgerMenu from "./BurgerMenu";
import {Fade, Slide} from "react-awesome-reveal";


type Props = {
    onSignedIn: () => void,
}

function HomePage({onSignedIn}: Props) {
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
                {allCategories.map((category: string) => {
                    return (
                        <Link className={"link"} to={`${category}`} key={category}>
                            <Category category={category}/>
                        </Link>
                    )
                })}
                <section className={"for-add-category"}>
                    <img width={50} src="/add-category.png" alt=""/>
                </section>
            </section>
        </section>
    );
}

export default HomePage;