import {useNavigate} from "react-router-dom";
import {FormEvent, useState} from "react";
import {FoodSpotUserWithoutId} from "../types/FoodSpotUserWithoutId.ts";
import SeePassword from "../icons/SeePassword.tsx";

type Props = {
    onRegistration: (newUser: FoodSpotUserWithoutId) => void
}

function SignUpPage({onRegistration}: Props) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [repeatedPassword, setRepeatedPassword] = useState<string>("")
    const [showPassword, setShowPassword] = useState<boolean>(false)
    const [showRepeatedPassword, setShowRepeatedPassword] = useState<boolean>(false)

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

    function handleShowPassword() {
        setShowPassword(!showPassword)
    }

    function handleShowRepeatedPassword() {
        setShowRepeatedPassword(!showRepeatedPassword)
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
                    <section className={"form-section-container password"}>
                        <input className={"add-form-input"}
                               placeholder={"Password"}
                               type={showPassword ? "text" : "password"}
                               name={"password"}
                               onChange={(e) => {
                                   setPassword(e.currentTarget.value)
                               }}
                               required
                        />
                        <SeePassword className={"password-icon"} size={"1.5em"} onShowPassword={handleShowPassword}
                                     currentShowValue={showPassword}/>
                    </section>
                    <section className={"form-section-container password"}>
                        <input className={"add-form-input"}
                               placeholder={"Repeat your password"}
                               type={showRepeatedPassword ? "text" : "password"}
                               name={"repeatedPassword"}
                               onChange={(e) => {
                                   setRepeatedPassword(e.currentTarget.value)
                               }}
                               required
                        />
                        <SeePassword className={"password-icon"} size={"1.5em"} onShowPassword={handleShowRepeatedPassword}
                                     currentShowValue={showRepeatedPassword}/>
                    </section>
                </section>
                <section className={"add-button-container"}>
                    <button className={"add-button"}>Sign-up</button>
                </section>
            </form>
        </section>
    );
}

export default SignUpPage;
