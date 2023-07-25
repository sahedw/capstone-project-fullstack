import {allCategories} from "../utils/allCategories.ts";
import category from "./Category.tsx";

function AddForm() {
    return (
        <section className={"form-add-container"}>
            <form className={"form"}>
                <label htmlFor="name">Name of FoodSpot:</label>
                <input type="text" name={"name"}/>

                <label htmlFor="category">Category:</label>
                <select name="category" id="category">
                    <option value="default" disabled={true}>Choose the category</option>
                    {allCategories.map((category: string) => {
                        return (
                            <option value={`${category}`} key={category}>{category}</option>
                        )
                    })}
                </select>

                <label htmlFor="address">Address:</label>
                <input type="text" name={"address"}/>
            </form>
        </section>
    );
}

export default AddForm;