import {FoodSpot} from "../types/FoodSpot";
import {Link} from "react-router-dom";
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters";
import {Category} from "../types/Category.ts";

type Props = {
    foodSpots: FoodSpot[],
    categories: Category[]
}
function ListView({foodSpots, categories}: Props) {

    const currentCategoryName: string = foodSpots[0].category;

    const currentCategory: Category | undefined = categories.find((category) => category.name === currentCategoryName)
    return (
        <section className={"category-card-grid"}>
            {foodSpots.map((foodSpot: FoodSpot) => {
                return (
                    <Link to={`/${foodSpot.category}/${foodSpot.id}`} className={"link"} key={foodSpot.id}>
                        <div className={"foodspot-card-container"} key={foodSpot.id}>
                            <h3>{foodSpot.name}</h3>
                            <img className={`card-image ${convertGermanSpecialCharacters(foodSpot.category, true)}`} src={currentCategory !== undefined ? currentCategory.imageCSSDetails.foodSpotCard.cloudinaryUrl : "https://api-prod.sevencooks.com/img/general/vegetarisches_rezept_klassische_ramen_suppe_3-1244661-0-1140-6.jpg"}
                                 alt="food image"/>
                        </div>
                    </Link>
                )
            })}
        </section>
    );
}

export default ListView;
