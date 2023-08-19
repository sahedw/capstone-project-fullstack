import {FormEvent, Fragment, useState} from "react";
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
    const [leftPixel, setLeftPixel] = useState<number>(0)
    const [topPixel, setTopPixel] = useState<number>(0)
    const [imageWidth, setImageWidth] = useState<number>(100)


    const MySwal = withReactContent(Swal)

    function handleUpPositioning() {
        setTopPixel(topPixel - 5)
    }

    function handleDownPositioning() {
        setTopPixel(topPixel + 5)
    }

    function handleLeftPositioning() {
        setLeftPixel(leftPixel - 5)
    }

    function handleRightPositioning() {
        setLeftPixel(leftPixel + 5)
    }

    function handleSmallerWidth() {
        if (imageWidth > 10)
            setImageWidth(imageWidth - 10)
    }

    function handleBiggerWidth() {
        setImageWidth(imageWidth + 10)
    }

    function handleSubmitForm(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const newCategory: Category = {
            name: categoryName?.toUpperCase()
        }
        let isDuplicate = false;
        for (const category of categories) {
            if (category.name === newCategory.name) {
                isDuplicate = true;
            }
        }

        if (!isDuplicate) {
            axios.post("/api/user/categories", newCategory)
                .then(() => {
                    onCategories()
                    toast("CategoryCard added!", {
                        duration: 1000,
                        icon: '🎉',
                        style: {
                            border: '2px solid #713200',
                            padding: '10px',
                            color: 'black',
                            boxShadow: "8px 8px 0px -2px #000000",
                            backgroundColor: "lightgreen"

                        }
                    })
                })
                .then(() => {
                    // handlePreviewAndEdit()
                    setPreviewMode(true)
                })
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

    function handlePreviewAndEdit() {
        MySwal.fire({
            html: <>
                <p className={"sweetalert2-title"}>Position your images:</p>
                <br/>
                <section className={"category-container"}>
                    <h4 className={"category-header"}>{categoryName?.toUpperCase()}</h4>
                    <img style={{
                        left: `${leftPixel.toString()}px`,
                        width: `${imageWidth.toString()}px`,
                    }} className={`category-container-image`} src={`${categoryName?.toUpperCase()}-BG.png`}
                         alt={categoryName}/>
                </section>
                <section>
                    <button onClick={handleLeftPositioning}>Left</button>
                    <button onClick={handleRightPositioning}>Right</button>
                    <button onClick={handleSmallerWidth}>Smaller</button>
                    <button onClick={handleBiggerWidth}>Bigger</button>
                </section>
            </>
        })
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
                        <form className={"form form-center"}>
                            <section className={"banner"}>
                                <img width={80} src="/banner.png" alt="free banner"/>
                            </section>
                            <section className={"form-header-container"}>
                                <h3>Position your images:</h3>
                            </section>
                            <section className={"category-container"}>
                                <h4 className={"category-header"}>{categoryName?.toUpperCase()}</h4>
                                <img style={{
                                    left: `${leftPixel.toString()}px`,
                                    top: `${topPixel.toString()}px`,
                                    width: `${imageWidth.toString()}px`
                                }} className={`category-container-image`} src={`${categoryName?.toUpperCase()}-BG.png`}
                                     alt={categoryName}/>
                            </section>
                            <section>
                                <button type={"button"} onClick={handleLeftPositioning}>Left</button>
                                <button type={"button"} onClick={handleRightPositioning}>Right</button>
                                <button type={"button"} onClick={handleSmallerWidth}>Smaller</button>
                                <button type={"button"} onClick={handleBiggerWidth}>Bigger</button>
                                <button type={"button"} onClick={handleUpPositioning}>Up</button>
                                <button type={"button"} onClick={handleDownPositioning}>Down</button>
                            </section>
                            <input type="file"/>
                            <section className={"add-button-container"}>
                                <button className={"add-button"}>Preview your category!</button>
                            </section>
                        </form>
                    </section>
                </>
            }
        </>
    );
}


export default AddCategoryForm;