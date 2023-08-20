import {Category} from "../types/Category.ts";
import BurgerMenu from "./BurgerMenu.tsx";
import BackButton from "./BackButton.tsx";
import toast, {Toaster} from "react-hot-toast";
import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";

type Props = {
    onDeleteCategories: (categoryToDelete: string) => void,
    categories: Category[]
}
function DeleteCategoryForm({onDeleteCategories, categories}: Props) {
    const [categoryToDelete, setCategoryToDelete] = useState<string>("")

    const navigate = useNavigate();
    function handleDeleteFormSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()

        for (const category of categories) {
            console.log(category)
            console.log(categoryToDelete)
            if (category.name === categoryToDelete) {
                onDeleteCategories(category.id)
                toast("Deleted Category!", {
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
            }
        }
        setTimeout(() => {
            navigate("/")
        }, 1500);
    }
    return (
        <>
            <div><Toaster/></div>
            <section className={"overflow-menu"}>
                <BurgerMenu/>
                <BackButton setClass={"normal"}/>
                <div><Toaster/></div>
                <section className={"form-add-container"}>
                    <form onSubmit={handleDeleteFormSubmit} className={"form form-add-category-small"}>
                        <section className={"banner"}>
                            <img width={80} src="/banner.png" alt="free banner"/>
                        </section>
                        <section className={"banner-text"}>
                            <strong>FREE</strong>
                        </section>
                        <section className={"form-header-container"}>
                            <h3>Don't need a  category anymore?</h3>
                        </section>
                        <section className={"form-main-container"}>
                            <section className={"form-section-container"}>
                                <select
                                    onChange={(e) => {
                                        setCategoryToDelete(e.currentTarget.value)
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
                            </section>
                        </section>
                        <section className={"add-button-container"}>
                            <button className={"add-button"}>Delete your category.</button>
                        </section>
                    </form>
                </section>
            </section>
        </>
    );
}

export default DeleteCategoryForm;