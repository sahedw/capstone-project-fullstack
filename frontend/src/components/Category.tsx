type Props = {
    category: string
}
function Category({category}: Props) {
    return (
        <section className={"category-container"}>
            <h4 className={"category-header"}>{category == "DOENER" ? "DÖNER" : category}</h4>
            <img className={"category-container-image"} src={`${category}-BG.png`} alt="food"/>
        </section>
    );
}

export default Category;