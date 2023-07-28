import {FormEvent, useState} from 'react';
import {FoodSpot} from "../types/FoodSpot.ts";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId.ts";
import {useNavigate} from "react-router-dom";

type Props = {
    onEditMode: () => void,
    foodSpot: FoodSpot,
    onUpdate: (id: string, updatedFoodSpot: FoodSpotWithoutId) => void,
    onDelete: (id: string) => void
}
function EditForm({onEditMode, foodSpot, onUpdate, onDelete}: Props) {
    const [name, setName] = useState<string>(foodSpot.name);
    const [address, setAddress] = useState<string>(foodSpot.address);
    const navigate = useNavigate();
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

    function handleDelete(id: string): void {
        onDelete(id);
        navigate(-1)
    }
    return (
        <>
            <form onSubmit={handleSubmitUpdateForm} className={"form-update"}>
                <section className={"form-header-container"}>
                    <h2>Wanna edit your FoodSpot?</h2>
                    <p>Go for it, important things need care:</p>
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
                <section className={"form-edit-buttons-container"}>
                    <section>
                        <button type={"submit"} className={"edit-button save-changes"}>
                            <img width={40} src="/save.png" alt="approve changes"/>
                        </button>
                    </section>
                    <section>
                        <button className={"delete-button"} type={"button"} onClick={() => {
                            handleDelete(foodSpot.id)
                        }}>
                            <img width={35} src="/delete.png" alt="approve changes"/>
                        </button>
                    </section>
                </section>
            </form>

        </>
    );
}

export default EditForm;
