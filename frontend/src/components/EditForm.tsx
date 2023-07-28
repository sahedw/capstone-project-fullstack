import {FormEvent, useState} from 'react';
import {FoodSpot} from "../types/FoodSpot.ts";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId.ts";

type Props = {
    onEditMode: () => void,
    foodSpot: FoodSpot,
    onUpdate: (id: string, updatedFoodSpot: FoodSpotWithoutId) => void
}
function EditForm({onEditMode, foodSpot, onUpdate}: Props) {
    const [name, setName] = useState<string>(foodSpot.name);
    const [address, setAddress] = useState<string>(foodSpot.address);
    function handleSubmitUpdateForm(e: FormEvent) {
        e.preventDefault()
        const updatedFoodSpot: FoodSpotWithoutId = {
            name: name,
            address: address,
            category: foodSpot.category
        }
        onUpdate(foodSpot.id, updatedFoodSpot)
        onEditMode()
    }
    return (
        <>
            <form onSubmit={handleSubmitUpdateForm}>
                <section className={"form-header-container"}>
                    <h2>Wanna edit your FoodSpot?</h2>
                    <p>Go for it, important things need care!</p>
                </section>
                <section className={"form-main-container"}>
                    <section className={"form-section-container"}>
                        <input className={"add-form-input"}
                               type="text"
                               name={"name"}
                               value={name}
                               onChange={(e) => {
                                   setName(e.currentTarget.value)
                               }}
                               required
                        />
                    </section>
                    <section className={"form-section-container"}>
                        <input className={"add-form-input"}
                               type="text"
                               name={"address"}
                               value={address}
                               onChange={(e) => {
                                   setAddress(e.currentTarget.value)
                               }}
                               required
                        />
                    </section>
                </section>
                <section>
                    <button className={"edit-button"}>
                        <img width={40} src="/save.png" alt="approve changes"/>
                    </button>
                </section>
            </form>
        </>
    );
}

export default EditForm;