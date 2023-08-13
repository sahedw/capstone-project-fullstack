import {FoodSpot} from "../types/FoodSpot.ts";
import {useEffect, useState} from "react";
import axios from "axios";
import BurgerMenu from "./BurgerMenu.tsx";
import toast, {Toaster} from "react-hot-toast";
import returnRandomString from "../utils/returnRandomString.ts";
import Refresh from "../icons/Refresh.tsx";
import SaveDisk from "../icons/SaveDisk.tsx";

type Props = {
    user: string | undefined,
    foodSpots: FoodSpot[],
    onLogout: () => void
}

function AccountPage({user, foodSpots, onLogout}: Props) {
    const [city, setCity] = useState<string>("")
    const [seed, setSeed] = useState<string>("Buster")
    const [currentSeed, setCurrentSeed] = useState<string>("")

    useEffect(() => {
        axios.get("/api/user/city")
            .then((response) => {
                setCity(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])

    function handleLogout() {
        onLogout();
        setTimeout(() => {
            toast("Come back soon!", {
                duration: 1000,
                icon: '👋🏼',
                style: {
                    border: '2px solid #713200',
                    padding: '10px',
                    color: 'black',
                    boxShadow: "8px 8px 0px -2px #000000",
                    backgroundColor: "white"
                }
            })
        }, 500);
    }

    function handleSeed() {
        const string: string = returnRandomString();
        setSeed(string) ;
    }

    function handleSaveSeed() {
        const currentUser = {
            username: `${user != undefined ? user : ""}`,
            seed: seed
        }
        axios.post("/api/user/picture-seed", currentUser)
            .then(response => {
                setCurrentSeed(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
        toast("Saved!", {
            duration: 1500,
            icon: '🎉',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "lightgreen"

            }
        })
    }

    useEffect(() => {
        axios.get("/api/user/picture-seed")
            .then(response => {
                setCurrentSeed(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    })



    return (
        <>
            <div><Toaster/></div>
            <section className={"overflow-menu"}>
                <BurgerMenu/>
                <section className={"account-container"}>
                    <section className={"account-card-container"}>
                        <section className={"account-info-container"}>
                            <section>
                                {currentSeed === "" ?
                                <img className={"account-profile-picture"}
                                     src={`https://api.dicebear.com/6.x/lorelei/svg?seed=${seed}&radius=50&backgroundColor=b6e3f4,c0aede,ffdfbf,ffd5dc,d1d4f9`}
                                     alt="avatar"/>
                                    :
                                    <img className={"account-profile-picture"}
                                         src={`https://api.dicebear.com/6.x/lorelei/svg?seed=${seed !== "Buster" ? seed : currentSeed}&radius=50&backgroundColor=b6e3f4,c0aede,ffdfbf,ffd5dc,d1d4f9`}
                                         alt="avatar"/>
                                }
                                <section className={"account-picture-buttons-container"}>
                                    <button onClick={handleSeed} className={"account-picture-button"}>
                                        <Refresh size={"1.7em"}/>
                                    </button>
                                    <button onClick={handleSaveSeed} className={"account-picture-button"}>
                                        <SaveDisk size={"1.7em"}/>
                                    </button>
                                </section>
                            </section>

                            <section className={"account-info-text"}>
                                <h3>{user}</h3>
                                <p>{city}</p>
                                <p>Currently {foodSpots.length} Spots saved</p>
                                <button onClick={handleLogout}>Logout</button>
                            </section>
                        </section>
                    </section>
                </section>
            </section>
        </>
    );
}

export default AccountPage;