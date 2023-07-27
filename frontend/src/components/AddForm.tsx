import {allCategories} from "../utils/allCategories.ts";
import {FormEvent, useState} from "react";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId.ts";
import BackButton from "./BackButton.tsx";

type Props = {
    onAdd: (newFoodSpot: FoodSpotWithoutId) => void;
}

function AddForm({onAdd}: Props) {

    const [name, setName] = useState<string>()
    const [category, setCategory] = useState<string>()
    const [address, setAddress] = useState<string>()

    function handleAddFormSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const newDtoFoodSpot: FoodSpotWithoutId = {
            name: name,
            category: category,
            address: address
        };
        onAdd(newDtoFoodSpot)
    }

    return (
        <section>
            <section className={"form-add-container"}>
                <form onSubmit={handleAddFormSubmit} className={"form"}>
                    <BackButton setClass={"from-form"}/>
                    <section className={"banner"}>
                        <img width={80} src="/banner.png" alt="free banner"/>
                    </section>
                    <section className={"banner-text"}>
                        <strong>FREE</strong>
                    </section>
                    <section className={"form-header-container"}>
                        <h2>You wanna add a FoodSpot?</h2>
                        <p>Nice, gatekeeping is for losers!</p>
                    </section>
                    <section className={"form-main-container"}>
                        <section className={"form-section-container"}>
                            <input className={"add-form-input"}
                                   placeholder={"Name of the FoodSpot"}
                                   type="text"
                                   name={"name"}
                                   onChange={(e) => {
                                       setName(e.currentTarget.value)
                                   }}
                                   required
                            />
                        </section>
                        <section className={"form-section-container"}>
                            <input className={"add-form-input"}
                                   placeholder={"Address"}
                                   type="text"
                                   name={"address"}
                                   onChange={(e) => {
                                       setAddress(e.currentTarget.value)
                                   }}
                                   required
                            />
                        </section>
                        <section className={"form-section-container"}>
                            <select
                                onChange={(e) => {
                                    setCategory(e.currentTarget.value)
                                }}
                                name="category"
                                id="category"
                                defaultValue={"default"}
                                required>
                                <option value="default" disabled={true} hidden>Choose the category</option>
                                {allCategories.map((category: string) => {
                                    return (
                                        <option value={`${category}`} key={category}>{category}</option>
                                    )
                                })}
                            </select>
                        </section>
                    </section>
                    <section className={"add-button-container"}>
                        <button className={"add-button"}>Add FoodSpot to Collection!</button>
                    </section>
                </form>
            </section>
        </section>
    );
}

export default AddForm;
