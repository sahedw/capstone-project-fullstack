import {FormEvent, useState} from 'react';
import {FoodSpot} from "../types/FoodSpot.ts";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId.ts";
import {useNavigate} from "react-router-dom";
import ChoosePriceLevels from "../icons/ChoosePriceLevels.tsx";
import getPriceLevelEnum from "../utils/getPriceLevelEnum.ts";
import {allCategories} from "../utils/allCategories.ts";
import toast, {Toaster} from "react-hot-toast";
import AutocompleteInput from "./AutocompleteInput.tsx";

type EditMode = () => void;

type Props = {
    onEditMode: () => void,
    foodSpot: FoodSpot,
    onUpdate: (id: string, updatedFoodSpot: FoodSpotWithoutId, editMode: EditMode) => void,
    onDelete: (id: string) => void,
}

function EditForm({onEditMode, foodSpot, onUpdate, onDelete}: Props) {
    const [name, setName] = useState<string>(foodSpot.name);
    const [address, setAddress] = useState<string>(foodSpot.address);
    const [category, setCategory] = useState<string>(foodSpot.category)
    const [instagramUsername, setInstagramUsername] = useState<string>("")
    const [priceLevel, setPriceLevel] = useState<boolean[]>([true, false, false])

    const navigate = useNavigate();

    function handleSubmitUpdateForm(e: FormEvent) {
        e.preventDefault()
        const updatedFoodSpot: FoodSpotWithoutId = {
            name: name,
            address: address,
            category: category,
            instagramUsername: instagramUsername,
            priceLevel: getPriceLevelEnum(priceLevel.filter((level) => level).length)
        }
        const addFoodSpotToast = toast.loading('Add to collection...', {
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "#f3d935"

            }
        });
        setTimeout(() => {
            toast.dismiss(addFoodSpotToast);
            onUpdate(foodSpot.id, updatedFoodSpot, onEditMode)
            if (foodSpot.category !== category) {
                navigate("/"+foodSpot.category, {replace: true});
            }
        }, 1500);
    }

    function handlePriceLevel(pickedPriceLevel: number) {
        if (pickedPriceLevel == 1) {
            setPriceLevel([true, false, false])
        } else if (pickedPriceLevel == 2) {
            setPriceLevel([true, !priceLevel[1], false])
        } else {
            setPriceLevel([true, true, !priceLevel[2]])
        }
    }

    function handleDelete(id: string): void {
        toast("Deleted FoodSpot!", {
            duration: 1500,
            icon: '🚮',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "orangered"

            }
        })
        setTimeout(() => {
        onDelete(id);
            navigate(-1)
        }, 1500);
    }

    function handleSelectPlace(place: any) {
        setAddress(place.label)
    }

    return (
        <>
            <div><Toaster/></div>
            <form onSubmit={handleSubmitUpdateForm} className={"form-update"}>
                <section className={"form-header-container"}>
                    <h2>Wanna edit your FoodSpot?</h2>
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
                        <ul className={"requirement-list-container"}>
                            <li className={name.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                            <li className={name.length < 2 ? "invalid" : "valid"}>Must contain at least 2
                                characters
                            </li>
                        </ul>
                    </section>
                    <section className={"form-section-container"}>
                        <AutocompleteInput onSelectPlace={handleSelectPlace} placeholder={foodSpot.address} shadowPixel={"12"}/>
                    <ul className={"requirement-list-container"}>
                            <li className={address.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                            <li className={address.length < 5 ? "invalid" : "valid"}>Must contain at least 5
                                characters
                            </li>
                        </ul>
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
                            {allCategories.map((category: string) => {
                                return (
                                    <option value={`${category}`}
                                            key={category}>{category === "DOENER" ? "DÖNER" : category}</option>
                                )
                            })}
                        </select>
                        <ul className={"requirement-list-container"}>
                            <li className={category === "" ? "invalid" : "valid"}>Mandatory pick</li>
                        </ul>
                    </section>
                    <section className={"form-section-container"}>
                        <section className={"form-bottom-inputs"}>
                            <input type="text"
                                   className={"input-instagram"}
                                   placeholder={"Insta-Username"}
                                   name={"instagram"}
                                   onChange={(e) => {
                                       setInstagramUsername(e.currentTarget.value)
                                   }}/>
                            <section>
                                <ChoosePriceLevels size={"1.5em"} onPriceLevel={handlePriceLevel}
                                                   priceLevel={priceLevel}/>
                            </section>
                        </section>
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
