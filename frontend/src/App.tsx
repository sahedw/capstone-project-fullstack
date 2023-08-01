import './App.css'
import {Fragment, useEffect, useState} from "react";
import {FoodSpot} from "./types/FoodSpot.ts";
import axios from "axios";
import {allCategories} from "./utils/allCategories.ts";
import {Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "./components/HomePage.tsx";
import FoodSpotCard from "./components/FoodSpotCard.tsx";
import AddForm from "./components/AddForm.tsx";
import {FoodSpotWithoutId} from "./types/FoodSpotWithoutId.ts";
import FoodSpotDetail from "./components/FoodSpotDetail.tsx";
import ProtectedPaths from "./components/ProtectedPaths.tsx";
import LoginPage from "./components/LoginPage.tsx";
import SignUpPage from "./components/SignUpPage.tsx";
import {FoodSpotUserWithoutId} from "./types/FoodSpotUserWithoutId.ts";
import toast from "react-hot-toast";


function App() {
    const [foodSpots, setFoodSpots] = useState<FoodSpot[]>([]);
    const [apiKey, setApiKey] = useState<string>("");
    const [user, setUser] = useState<string>()

    const navigate = useNavigate()

    function handleSignedIn() {
        axios.get("/api/user/account")
            .then(response => {
                setUser(response.data)
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
                navigate("/")
            })
    }

    function handleRegistration(newUserForRegistration: FoodSpotUserWithoutId) {
        axios.post("/api/user/sign-up", newUserForRegistration)
            .then(() => {
                handleLogin(newUserForRegistration.username, newUserForRegistration.password)
            })
            .catch(error => {
                console.log(error)
                navigate("/sign-up");
               // toast.error(`${error.response.data.message}`)
                toast(`${error.response.data.message}`, {
                    icon: '🤷🏻‍',
                    style: {
                        border: '2px solid #713200',
                        padding: '10px',
                        color: 'black',
                        boxShadow: "8px 8px 0px -2px #000000",
                        backgroundColor: "#f3d935"

                    }
                })
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
    }, [])

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
            .then(() => getAllFoodSpots())
            .catch(function (error) {
                console.error(error);
            });
    }

    function handleUpdateFoodSpot(id: string, updatedFoodSpot: FoodSpotWithoutId): void {
        axios.put(`/api/foodSpot/${id}`, updatedFoodSpot)
            .then(() => getAllFoodSpots())
            .catch(function (error) {
                console.error(error);
            });
    }

    function handleDeleteFoodSpot(id: string): void {
        axios.delete(`/api/foodSpot/${id}`)
            .then(() => getAllFoodSpots())
            .catch(function (error) {
                console.error(error);
            });
    }


    return (
        <>
            <Routes>
                <Route element={<ProtectedPaths user={user}/>}>
                    <Route path={"/"}
                           element={<HomePage onSignedIn={handleSignedIn} user={user} onLogout={handleLogout}/>}>
                    </Route>
                    <Route path={"/addFoodSpot"}
                           element={<AddForm onAdd={handleAddFoodSpot}/>}>
                    </Route>
                    {allCategories.map((category: string) => {
                        const filteredByCurrentCategory: FoodSpot[] = foodSpots.filter((spot: FoodSpot) => spot.category == category)
                        return (<Fragment key={category}>
                                <Route path={`/${category}`}
                                       element={<FoodSpotCard foodSpots={foodSpots}/>}>
                                </Route>
                                {filteredByCurrentCategory.map((foodSpot: FoodSpot) => {
                                    return (
                                        <Route path={`/${foodSpot.category}/${foodSpot.id}`} key={category + foodSpot.id}
                                               element={<FoodSpotDetail onDelete={handleDeleteFoodSpot}
                                                                        onUpdate={handleUpdateFoodSpot} apiKey={apiKey}
                                                                        foodSpot={foodSpot}/>}>
                                        </Route>
                                    )
                                })}
                            </Fragment>
                        )
                    })}
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
