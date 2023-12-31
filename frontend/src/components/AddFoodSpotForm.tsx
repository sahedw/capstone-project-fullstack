
import {FormEvent, Fragment, useEffect, useState} from "react";
import {FoodSpotWithoutId} from "../types/FoodSpotWithoutId";
import BackButton from "./BackButton";
import toast, {Toaster} from "react-hot-toast";
import ChoosePriceLevels from "../icons/ChoosePriceLevels";
import getPriceLevelEnum from "../utils/getPriceLevelEnum";
import AutocompleteInput from "./AutocompleteInput";
import BurgerMenu from "./BurgerMenu";
import CurrentLocation from "../icons/CurrentLocation";
import axios from "axios";
import {Position} from "../types/Position"
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import convertGermanSpecialCharacters from "../utils/convertGermanSpecialCharacters";
import SweetAlert2Option from "./SweetAlert2Option";
import {Category} from "../types/Category";


type Props = {
    onAdd: (newFoodSpot: FoodSpotWithoutId) => void,
    categories: Category[]
}

function AddFoodSpotForm({onAdd, categories}: Props) {

    const [name, setName] = useState<string>("")
    const [category, setCategory] = useState<string>("")
    const [address, setAddress] = useState<string>("")
    const [instagramUsername, setInstagramUsername] = useState<string>("")
    const [priceLevel, setPriceLevel] = useState<boolean[]>([true, false, false])
    const [currentAddressSuggestions, setCurrentAddressSuggestions] = useState<string[]>([])

    const MySwal = withReactContent(Swal)


    function handleAddFormSubmit(event: FormEvent<HTMLFormElement>) {
        console.log(category)
        event.preventDefault()
        const newDtoFoodSpot: FoodSpotWithoutId = {
            name: name,
            address: address,
            category: category,
            instagramUsername: instagramUsername,
            priceLevel: getPriceLevelEnum(priceLevel.filter((level) => level).length)
        };
        console.log(newDtoFoodSpot.category)

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
            toast.dismiss(addFoodSpotToast)
            onAdd(newDtoFoodSpot)
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

    function handleSelectPlace(place: any) {
        setAddress(place.label)
    }

    function handlePickedAddress(option: number) {
        if (option === 0) {
            navigator.clipboard.writeText(convertGermanSpecialCharacters(currentAddressSuggestions[0], false).replace(/,/g, ""))
        } else if (option === 1) {
            navigator.clipboard.writeText(convertGermanSpecialCharacters(currentAddressSuggestions[1], false).replace(/,/g, ""))
        } else {
            navigator.clipboard.writeText(convertGermanSpecialCharacters(currentAddressSuggestions[2], false).replace(/,/g, ""))
        }
        toast("Saved to clipboard!", {
            duration: 1500,
            icon: '🕺🏻',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "lightgreen"

            }
        })
    }

    // html: `<p>${convertGermanSpecialCharacters(currentAddressSuggestions[0], false).replace(/,/g, "")}</p><br><br><p>${convertGermanSpecialCharacters(currentAddressSuggestions[1], false).replace(/,/g, "")}</p><br><br><p>${convertGermanSpecialCharacters(currentAddressSuggestions[2], false).replace(/,/g, "")}</p>`

    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const {latitude, longitude} = position.coords;
                    const currentPosition: Position = {
                        latitude: latitude.toString(),
                        longitude: longitude.toString()
                    }
                    setTimeout(() => {
                        axios.post('/api/google/convert-latlng', currentPosition)
                            .then(response => {
                                setCurrentAddressSuggestions(response.data);
                                toast("Getting current position...", {
                                    duration: 1500,
                                    icon: '🕺🏻',
                                    style: {
                                        border: '2px solid #713200',
                                        padding: '10px',
                                        color: 'black',
                                        boxShadow: "8px 8px 0px -2px #000000",
                                        backgroundColor: "#f3d935"

                                    }
                                })
                            })
                            .catch(error => {
                                console.error(error);
                            });
                    }, 2000);
                })
        }
    }, [])

    const suggestions: string[] = currentAddressSuggestions.slice(0,3)

function handleShowPositionSuggetions() {
    MySwal.fire({
        html: <>
            <p className={"sweetalert2-title"} >Click to copy your current position:</p>
            <br/>
            {suggestions.map((address: string, index: number) => {
                return (
                    <Fragment key={address}>
                        <SweetAlert2Option onClick={() => {
                            handlePickedAddress(index)
                        }}
                                           text={convertGermanSpecialCharacters(address, false).replace(/,/g, "")}/>
                    </Fragment>
                )
                })

            }
        </>
    })
}



    return (
        <section className={"overflow-menu"}>
            <div><Toaster/></div>
            <BurgerMenu/>
            <section className={"form-add-container"}>
                <BackButton setClass={"normal"}/>
                <form onSubmit={handleAddFormSubmit} className={"form form-center"}>
                    <section className={"banner"}>
                        <img width={80} src="/banner.png" alt="free banner"/>
                    </section>
                    <section className={"banner-text"}>
                        <strong>FREE</strong>
                    </section>
                    <section className={"form-header-container"}>
                        <h2>You wanna add a FoodSpot?</h2>
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
                            <ul className={"requirement-list-container"}>
                                <li className={name.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                                <li className={name.length < 2 ? "invalid" : "valid"}>Must contain at least 2
                                    characters
                                </li>
                            </ul>
                        </section>
                        <section className={"form-section-container"}>
                            <AutocompleteInput onSelectPlace={handleSelectPlace}
                                               shadowPixel={"7"}/>
                            <CurrentLocation size={"30"} nameOfClass={"current-location-icon"}
                                             onClick={handleShowPositionSuggetions}/>
                            <ul className={"requirement-list-container"}>
                                <li className={address.trim().length === 0 ? "invalid" : "valid"}>Can't be blank
                                </li>
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
                                className={"category-select"}
                                required>
                                <option value="default" disabled={true} hidden>Choose the category</option>
                                {categories.map((category: Category) => {
                                    return (
                                        <option value={`${category.name}`}
                                                key={category.name}>{category.name === "DOENER" ? "DÖNER" : category.name}</option>
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
                                    <ChoosePriceLevels size={"2em"} onPriceLevel={handlePriceLevel}
                                                       priceLevel={priceLevel}/>
                                </section>
                            </section>
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

export default AddFoodSpotForm;
