import {Category} from "../types/Category";

type Props = {
    category: Category
}

function CategoryCard({category}: Props) {
    return (
        <section className={"category-container"}>
            <h4 className={"category-header"}>{category.name == "DOENER" ? "DÖNER" : category.name}</h4>
            <img style={{
                width: `${category.imageCSSDetails.categoryCard.imageWidth}px`,
                left: `${category.imageCSSDetails.categoryCard.leftPixel}px`,
                top: `${category.imageCSSDetails.categoryCard.topPixel}px`
            }}
                 className={`category-container-image`}
                 src={category.imageCSSDetails.categoryCard.cloudinaryUrl}
                 alt={category.name}/>
        </section>
    );
}

export default CategoryCard;