import './App.css'
import {Fragment, useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot";
import axios from "axios";
import {Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "./components/HomePage";
import FoodSpotCard from "./components/FoodSpotCard";
import AddFoodSpotForm from "./components/AddFoodSpotForm";
import {FoodSpotWithoutId} from "./types/FoodSpotWithoutId";
import FoodSpotDetail from "./components/FoodSpotDetail";
import ProtectedPaths from "./components/ProtectedPaths";
import LoginPage from "./components/LoginPage";
import SignUpPage from "./components/SignUpPage";
import {FoodSpotUserWithoutId} from "./types/FoodSpotUserWithoutId";
import toast from "react-hot-toast";
import HeaderJsLibraryApi from "./components/HeaderJSLibraryAPI";
import MapOverview from "./components/MapOverview";
import AccountPage from "./components/AccountPage";
import AddCategoryForm from "./components/AddCategoryForm";
import {Category} from "./types/Category";



function App() {
    const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);
    const [apiKey, setApiKey] = useState<string>("");
    const [user, setUser] = useState<string>()
    const [categories, setCategories] = useState<Category[]>([])

    const navigate = useNavigate()

    useEffect(() => {
        if (user !== undefined && user !== "anonymousUser")
        handleGetCategories()
    }, [user])

    function handleSignedIn() {
        axios.get("/api/user/account")
            .then(response => {
                setUser(response.data)
            })
    }

    function handleGetCategories() {
        axios.get("/api/user/categories")
            .then(response => {
                setCategories(response.data)
            })
    }

    function handleLogout() {
        axios.post("/api/user/logout")
            .then(() => {
                setUser("anonymousUser")
            })
    }

    function handleLogin(username: string, password: string) {
        axios.post("/api/user/login", null, {auth: {username, password}})
            .then(response => {
                setUser(response.data)
                toast("You're logged in!", {
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
                setTimeout(() => {
                    navigate("/")
                }, 1000);
            })
            .catch(error => {
                if (error.response.status === 401)
                    toast("Username doesn't exist.", {
                        duration: 1500,
                        icon: '🤷🏻‍',
                        style: {
                            border: '2px solid #713200',
                            padding: '10px',
                            color: 'black',
                            boxShadow: "8px 8px 0px -2px #000000",
                            backgroundColor: "orangered"

                        }
                    })
            });
    }

    function handleRegistration(newUserForRegistration: FoodSpotUserWithoutId) {
        axios.post("/api/user/sign-up", newUserForRegistration)
            .then(() => {
                handleLogin(newUserForRegistration.username, newUserForRegistration.password)
            })
            .catch(error => {
                console.log(error)
                navigate("/sign-up");
                if (error.response.status === 409) {
                    toast("Username already exists..", {
                        icon: '🤷🏻‍',
                        style: {
                            border: '2px solid #713200',
                            padding: '10px',
                            color: 'black',
                            boxShadow: "8px 8px 0px -2px #000000",
                            backgroundColor: "orangered"

                        }
                    })
                } else {
                    toast("Some requirements were not met", {
                        icon: '🤷🏻‍',
                        style: {
                            border: '2px solid #713200',
                            padding: '10px',
                            color: 'black',
                            boxShadow: "8px 8px 0px -2px #000000",
                            backgroundColor: "orangered"

                        }
                    })
                }

            })
    }

    useEffect(handleSignedIn, [user])

    useEffect(() => {
        axios.get('/api/google/key')
            .then(response => {
                setApiKey(response.data);
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [user])

    useEffect((): void => {
        if (user !== undefined && user !== "anonymousUser") {
            axios.get('/api/foodSpot')
                .then(response => {
                    setFoodSpots(response.data);
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }, [user])


    function getAllFoodSpots(): void {
        axios.get('/api/foodSpot')
            .then(response => {
                setFoodSpots(response.data);
            })
            .catch(function (error) {
                console.error(error);
            });
    }

    function handleAddFoodSpot(newFoodSpot: FoodSpotWithoutId): void {
        axios.post("/api/foodSpot", newFoodSpot)
            .then(() => {
                getAllFoodSpots()
                toast("Successful!", {
                    duration: 1000,
                    icon: '🕺🏻',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "lightgreen"

                    }
                })
            })
            .catch(function (error) {
                console.error(error);
                toast("Some requirements were not met", {
                    icon: '🤷🏻‍',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "orangered"

                    }
                })
            });
    }

    type EditMode = () => void;


    function handleUpdateFoodSpot(id: string, updatedFoodSpot: FoodSpotWithoutId, editMode: EditMode): void {
        axios.put(`/api/foodSpot/${id}`, updatedFoodSpot)
            .then(() => {
                getAllFoodSpots()
                toast("Saved Changes!", {
                    duration: 1500,
                    icon: '🫱🏼‍🫲🏽',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "lightgreen"

                    }
                })
                editMode()
            })
            .catch(function (error) {
                console.error(error);
                toast("Some requirements were not met", {
                    icon: '🤷🏻‍',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "orangered"

                    }
                })
            });
    }

    function handleDeleteFoodSpot(id: string): void {
        axios.delete(`/api/foodSpot/${id}`)
            .then(() => {
                getAllFoodSpots()
            })
            .catch(function (error) {
                console.error(error);
            });
    }


    return (
        <>
            {user !== undefined &&
                <HeaderJsLibraryApi apiKey={apiKey}/>
            }
            <Routes>
                <Route element={<ProtectedPaths user={user}/>}>
                    <Route path={"/"}
                           element={<HomePage onGetCategories={handleGetCategories} onSignedIn={handleSignedIn} categories={categories}/>}>
                    </Route>
                    <Route path={"/addFoodSpot"}
                           element={<AddFoodSpotForm categories={categories} onAdd={handleAddFoodSpot}/>}>
                    </Route>
                    <Route path={"/addCategory"}
                           element={<AddCategoryForm onCategories={handleGetCategories} categories={categories}/>}>
                    </Route>
                    {categories.map((category: Category) => {
                        const filteredByCurrentCategory: FoodSpot[] = foodSpots.filter((spot: FoodSpot) => spot.category == category.name)
                        return (<Fragment key={category.name}>
                                <Route path={`/${category.name}`}
                                       element={<FoodSpotCard foodSpots={foodSpots}/>}>
                                </Route>
                                {filteredByCurrentCategory.map((foodSpot: FoodSpot) => {
                                    return (
                                        <Route path={`/${foodSpot.category}/${foodSpot.id}`}
                                               key={category + foodSpot.id}
                                               element={<FoodSpotDetail onDelete={handleDeleteFoodSpot}
                                                                        onUpdate={handleUpdateFoodSpot}
                                                                        foodSpot={foodSpot}
                                                                        categories={categories}/>}>
                                        </Route>
                                    )
                                })}
                            </Fragment>
                        )
                    })}
                    <Route path={"/map"}
                           element={<MapOverview foodSpots={foodSpots}/>}>

                    </Route>
                    <Route path={"/account"}
                           element={<AccountPage user={user} foodSpots={foodSpots} onLogout={handleLogout}/>}>

                    </Route>
                </Route>
                <Route path={"/login"} element={<LoginPage onLogin={handleLogin}/>}>
                </Route>
                <Route path={"/sign-up"} element={<SignUpPage onRegistration={handleRegistration}/>}>
                </Route>
            </Routes>
        </>
    )
}

export default App
