type Props = {
    category: string
}
function CategoryCard({category}: Props) {
    return (
        <section className={"category-container"}>
            <h4 className={"category-header"}>{category == "DOENER" ? "DÖNER" : category}</h4>
            <img className={`category-container-image ${category}`} src={`${category}-BG.png`} alt={category}/>
        </section>
    );
}

export default CategoryCard;