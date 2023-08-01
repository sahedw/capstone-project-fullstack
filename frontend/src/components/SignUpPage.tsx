import {useNavigate} from "react-router-dom";
import {FormEvent, useState} from "react";
import {FoodSpotUserWithoutId} from "../types/FoodSpotUserWithoutId.ts";

type Props = {
    onRegistration: (newUser: FoodSpotUserWithoutId) => void
}
function SignUpPage({onRegistration}: Props) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [repeatedPassword, setRepeatedPassword] = useState<string>("")

    const navigate = useNavigate()

    function isIdentical(password: string, secondPassword: string) {
        return password === secondPassword;
    }


    function handleSignUpSubmit(e: FormEvent) {
        e.preventDefault()
        if (isIdentical(password, repeatedPassword)) {
            const newUser = {
                username: username,
                password: password
            }
            onRegistration(newUser);
            navigate("/");
        } else {
            alert("Password is not identical")
        }
    }

    return (
        <section className={"form-add-container"}>
            <form onSubmit={handleSignUpSubmit} className={"form login"}>
                <section className={"form-header-container"}>
                    <h2>Insert your details:</h2>
                </section>
                <section className={"form-main-container"}>
                    <section className={"form-section-container"}>
                        <input className={"add-form-input"}
                               placeholder={"Username"}
                               type="text"
                               name={"username"}
                               onChange={(e) => {
                                   setUsername(e.currentTarget.value)
                               }}
                               required
                        />
                    </section>
                    <section className={"form-section-container"}>
                        <input className={"add-form-input"}
                               placeholder={"Password"}
                               type="text"
                               name={"password"}
                               onChange={(e) => {
                                   setPassword(e.currentTarget.value)
                               }}
                               required
                        />
                    </section>
                    <section className={"form-section-container"}>
                        <input className={"add-form-input"}
                               placeholder={"Repeat your password"}
                               type="text"
                               name={"repeatedPassword"}
                               onChange={(e) => {
                                   setRepeatedPassword(e.currentTarget.value)
                               }}
                               required
                        />
                    </section>
                </section>
                <section className={"add-button-container"}>
                    <button className={"add-button"}>Login</button>
                </section>
            </form>
        </section>
    );
}

export default SignUpPage;