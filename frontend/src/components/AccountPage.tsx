import {FoodSpot} from "../types/FoodSpot.ts";
import {useEffect, useState} from "react";
import axios from "axios";

type Props = {
    user: string,
    foodSpots: FoodSpot[]
}

function AccountPage({user, foodSpots}: Props) {
    const [city, setCity] = useState<string>("")

    useEffect(() => {
        axios.get("/api/user/city")
            .then((response) => {
                setCity(response.data)
            })
            .catch(function (error) {
                console.error(error);
            });
    }, [])


    return (
        <>
            <section className={"account-container"}>
                <section className={"account-card-container"}>
                    <section className={"account-info-container"}>
                        <img className={"account-profile-picture"}
                             src="https://api.dicebear.com/6.x/lorelei/svg?seed=Buster&radius=50&backgroundColor=b6e3f4,c0aede,ffdfbf,ffd5dc,d1d4f9"
                             alt="avatar"/>
                        <section className={"account-info-text"}>
                            <h3>{user}</h3>
                            <p>{city}</p>
                            <p>Currently {foodSpots.length} FoodSpots saved</p>
                            <button>Logout</button>
                        </section>
                    </section>
                </section>
            </section>
        </>
    );
}

export default AccountPage;