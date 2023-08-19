import {ChangeEvent, FormEvent, useState} from "react";
import toast, {Toaster} from "react-hot-toast";
import axios from "axios";
import {Category} from "../types/Category";
import withReactContent from "sweetalert2-react-content";
import Swal from "sweetalert2";


type Props = {
    categories: Category[],
    onCategories: () => void
}

function AddCategoryForm({categories, onCategories}: Props) {
    const [categoryName, setCategoryName] = useState<string>()
    const [previewMode, setPreviewMode] = useState<boolean>(false)
    const [leftPixelBG, setLeftPixelBG] = useState<number>(0)
    const [topPixelBG, setTopPixelBG] = useState<number>(0)
    const [imageWidthBG, setImageWidthBG] = useState<number>(100)
    const [imageWidthNormal, setImageWidthNormal] = useState<number>(100)
    const [selectedBGImage, setSelectedBGImage] = useState<File | null>(null)
    const [selectedNormalImage, setSelectedNormalImage] = useState<File | null>(null)


    const MySwal = withReactContent(Swal)

    function handleUpPositioning() {
        setTopPixelBG(topPixelBG - 5)
    }

    function handleDownPositioning() {
        setTopPixelBG(topPixelBG + 5)
    }

    function handleLeftPositioning() {
        setLeftPixelBG(leftPixelBG - 5)
    }

    function handleRightPositioning() {
        setLeftPixelBG(leftPixelBG + 5)
    }

    function handleSmallerWidth(type: string) {
        if (type === "BG") {
            if (imageWidthBG > 10)
                setImageWidthBG(imageWidthBG - 10)
        } else {
            setImageWidthNormal(imageWidthNormal - 10)
        }
    }

    function handleBiggerWidth(type: string) {
        if (type === "BG") {
            setImageWidthBG(imageWidthBG + 10)

        } else {
            setImageWidthNormal(imageWidthNormal + 10)
        }

    }

    function handleBGFileChange(event: ChangeEvent<HTMLInputElement>) {
        const file = event.target.files?.[0];
        setSelectedBGImage(file);
    }

    function handleNormalFileChange(event: ChangeEvent<HTMLInputElement>) {
        const file = event.target.files?.[0];
        setSelectedNormalImage(file);
    }

    function handleSubmitForm(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()

        let isDuplicate = false;
        for (const category of categories) {
            if (category.name === categoryName) {
                isDuplicate = true;
            }
        }

        if (!isDuplicate) {
            // handlePreviewAndEdit()
            setPreviewMode(true)
        } else {
            toast("CategoryCard already exists.", {
                icon: '👀',
                style: {
                    border: '2px solid #713200',
                    padding: '10px',
                    color: 'black',
                    boxShadow: "8px 8px 0px -2px #000000",
                    backgroundColor: "orangered"

                }
            })
        }
    }

    function handleSubmitCategoryDetails(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()

        const categoryWithDetails: Category = {
            name: categoryName?.toUpperCase(),
            imageCSSDetails: {
                categoryCard: {
                    leftPixel: leftPixelBG,
                    imageWidth: imageWidthBG
                },
                foodSpotCard: {
                    imageWidth: imageWidthNormal
                }
            }
        }
        axios.post("/api/user/categories", categoryWithDetails)
            .then(() => {
                onCategories()
            })
            .then(() => {
                toast("Category was added!", {
                    icon: '🎉',
                    duration: 1500,
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "lightgreen"

                    }
                })
            })
        setTimeout(() => {
            setPreviewMode(!previewMode)
        }, 2000);
    }

    return (<>
            {!previewMode ?
                <>
                    <div><Toaster/></div>
                    <section className={"form-add-container"}>
                        <form onSubmit={handleSubmitForm} className={"form form-center"}>
                            <section className={"banner"}>
                                <img width={80} src="/banner.png" alt="free banner"/>
                            </section>
                            <section className={"banner-text"}>
                                <strong>FREE</strong>
                            </section>
                            <section className={"form-header-container"}>
                                <h3>Some more categories? Good idea.</h3>
                            </section>
                            <section className={"form-main-container"}>
                                <section className={"form-section-container"}>
                                    <input className={"add-form-input"}
                                           placeholder={"Name of the category"}
                                           type="text"
                                           name={"name"}
                                           onChange={(e) => {
                                               setCategoryName(e.currentTarget.value)
                                           }}
                                           required
                                    />
                                    {categoryName?.length > 0 &&
                                        <ul className={"requirement-list-container"}>
                                            <li className={categoryName.trim().length === 0 ? "invalid" : "valid"}>Can't
                                                be
                                                blank
                                            </li>
                                        </ul>
                                    }
                                </section>
                            </section>
                            <section className={"add-button-container"}>
                                <button className={"add-button"}>Preview your category!</button>
                            </section>
                        </form>
                    </section>
                </>
                :
                <>
                    <div><Toaster/></div>
                    <section className={"form-add-container"}>
                        <form onSubmit={handleSubmitCategoryDetails} className={"form form-center form-add-category"}>
                            <section className={"banner"}>
                                <img width={80} src="/banner.png" alt="free banner"/>
                            </section>
                            <section className={"banner-text"}>
                                <strong>FREE</strong>
                            </section>
                            <section className={"form-header-container"}>
                                <h3>Position your images:</h3>
                            </section>
                            <section className={"category-container"}>
                                <h4 className={"category-header"}>{categoryName?.toUpperCase()}</h4>
                                {selectedBGImage !== null &&
                                    <img style={{
                                        left: `${leftPixelBG.toString()}px`,
                                        top: `${topPixelBG.toString()}px`,
                                        width: `${imageWidthBG.toString()}px`
                                    }} className={`category-container-image`}
                                         src={URL?.createObjectURL(selectedBGImage)}
                                         alt={categoryName}/>
                                }
                            </section>
                            <section>
                                <section>
                                    <button type={"button"} onClick={() => {
                                        handleLeftPositioning()
                                    }}>Left
                                    </button>
                                    <button type={"button"} onClick={() => {
                                        handleRightPositioning()
                                    }}>Right
                                    </button>
                                    <button type={"button"} onClick={() => {
                                        handleSmallerWidth("BG")
                                    }}>Smaller
                                    </button>
                                    <button type={"button"} onClick={() => {
                                        handleBiggerWidth("BG")
                                    }}>Bigger
                                    </button>
                                </section>
                                <button type={"button"} onClick={() => {
                                    handleUpPositioning()
                                }}>Up
                                </button>
                                <button type={"button"} onClick={() => {
                                    handleDownPositioning()
                                }}>Down
                                </button>
                            </section>
                            <input type="file" onChange={handleBGFileChange}/>
                            <br/>
                            <div className={"foodspot-card-container"}>
                                <h3>Spot-Name</h3>
                                {selectedNormalImage !== null &&
                                    <img style={{
                                        width: `${imageWidthNormal.toString()}px`
                                    }} className={`card-image`} src={URL?.createObjectURL(selectedNormalImage)}
                                         alt="food image"/>
                                }
                            </div>
                            <section>
                                <section>
                                    <button type={"button"} onClick={() => {
                                        handleSmallerWidth("Normal")
                                    }}>Smaller
                                    </button>
                                    <button type={"button"} onClick={() => {
                                        handleBiggerWidth("Normal")
                                    }}>Bigger
                                    </button>
                                </section>
                            </section>
                            <input type="file" onChange={handleNormalFileChange}/>
                            <section className={"add-button-container"}>
                                <button className={"add-button"}>Submit your category!</button>
                            </section>
                        </form>
                    </section>
                </>
            }
        </>
    );
}


export default AddCategoryForm;