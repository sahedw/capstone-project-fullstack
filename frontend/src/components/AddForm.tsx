import {allCategories} from "../utils/allCategories.ts";
import {FormEvent, useState} from "react";
import {DtoFoodSpot} from "../types/DtoFoodSpot.ts";

type Props = {
    onAdd: (newFoodSpot: DtoFoodSpot) => void;
}

function AddForm({onAdd}: Props) {

    const [name, setName] = useState<string>()
    const [category, setCategory] = useState<string>()
    const [address, setAddress] = useState<string>()

    function handleAddFormSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const newDtoFoodSpot: DtoFoodSpot = {
            name: name,
            category: category,
            address: address
        };
        onAdd(newDtoFoodSpot)
    }

    return (
        <section>
            <section>
                <h3>You wanna add a FoodSpot?</h3>
                <p>Nice, gatekeeping is for losers!</p>
            </section>
            <section className={"form-add-container"}>
                <form onSubmit={handleAddFormSubmit} className={"form"}>
                    <section>
                        <label htmlFor="name">Name of FoodSpot:</label>
                        <input type="text"
                               name={"name"}
                               onChange={(e) => {
                                   setName(e.currentTarget.value)
                               }}
                        />
                    </section>
                    <section>
                        <label htmlFor="category">Category:</label>
                        <select
                            onChange={(e) => {
                                setCategory(e.currentTarget.value)
                            }} name="category"
                            id="category">
                            <option value="default" disabled={true}>Choose the category</option>
                            {allCategories.map((category: string) => {
                                return (
                                    <option value={`${category}`} key={category}>{category}</option>
                                )
                            })}
                        </select>
                    </section>

                    <section>
                        <label htmlFor="address">Address:</label>
                        <input type="text"
                               name={"address"}
                               onChange={(e) => {
                                   setAddress(e.currentTarget.value)
                               }}/>
                    </section>
                    <section>
                        <button>Add FoodSpot to Collection!</button>
                    </section>
                </form>
            </section>
        </section>
    );
}

export default AddForm;