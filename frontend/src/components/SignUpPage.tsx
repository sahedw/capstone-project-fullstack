import {FormEvent, useState} from "react";
import {FoodSpotUserWithoutId} from "../types/FoodSpotUserWithoutId";
import SeePassword from "../icons/SeePassword";
import toast, {Toaster} from "react-hot-toast";
import BackButton from "./BackButton";

type Props = {
    onRegistration: (newUser: FoodSpotUserWithoutId) => void
}

function SignUpPage({onRegistration}: Props) {
    const [username, setUsername] = useState<string>("")
    const [city, setCity] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [repeatedPassword, setRepeatedPassword] = useState<string>("")
    const [showPassword, setShowPassword] = useState<boolean>(false)
    const [showRepeatedPassword, setShowRepeatedPassword] = useState<boolean>(false)

    function isIdentical(password: string, secondPassword: string) {
        return password === secondPassword;
    }


    function handleSignUpSubmit(e: FormEvent) {
        e.preventDefault()
        if (isIdentical(password, repeatedPassword)) {
            const newUser = {
                username: username,
                city: city,
                seed: "",
                password: password
            }
            const registerToast = toast.loading('Registering...', {
                style: {
                    border: '2px solid #713200',
                    padding: '10px',
                    color: 'black',
                    boxShadow: "8px 8px 0px -2px #000000",
                    backgroundColor: "#f3d935"

                }
            });
            setTimeout(() => {
                toast.dismiss(registerToast)
                onRegistration(newUser);
            }, 2000);
        } else {
            toast("Password must be identical", {
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

    function handleShowPassword() {
        setShowPassword(!showPassword)
    }

    function handleShowRepeatedPassword() {
        setShowRepeatedPassword(!showRepeatedPassword)
    }


    return (
        <>
            <div><Toaster/></div>
            <section className={"form-add-container"}>
                <BackButton setClass={"normal"}/>
                <form onSubmit={handleSignUpSubmit} className={"form register"}>
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
                            <ul className={"requirement-list-container"}>
                                <li className={username.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                                <li className={username.length < 5 ? "invalid" : "valid"}>Must contain at least 5
                                    characters
                                </li>
                            </ul>
                        </section>
                        <section className={"form-section-container"}>
                            <input className={"add-form-input"}
                                   placeholder={"City"}
                                   type="text"
                                   name={"city"}
                                   onChange={(e) => {
                                       setCity(e.currentTarget.value)
                                   }}
                                   required
                            />
                            <ul className={"requirement-list-container"}>
                                <li className={city.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                                <li className={city.length < 2 ? "invalid" : "valid"}>Must contain at least 2
                                    characters
                                </li>
                            </ul>
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
                            <ul className={"requirement-list-container"}>
                                <li className={password.trim().length === 0 ? "invalid" : "valid"}>Can't be blank</li>
                                <li className={password.length < 8 ? "invalid" : "valid"}>Must contain at least 8
                                    characters
                                </li>
                            </ul>
                            <SeePassword className={"password-icon"} size={"2em"} onShowPassword={handleShowPassword}
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
                            <ul className={"requirement-list-container"}>
                                {repeatedPassword.length > 0 ?
                                    <li className={repeatedPassword !== password
                                        ? "invalid" : "valid"}>
                                        Must be identical
                                    </li>
                                    :
                                    <></>
                                }
                            </ul>
                            <SeePassword className={"password-icon"} size={"2em"}
                                         onShowPassword={handleShowRepeatedPassword}
                                         currentShowValue={showRepeatedPassword}/>
                        </section>
                    </section>
                    <section className={"add-button-container"}>
                        <button className={"add-button"}>Sign-up</button>
                    </section>
                </form>
            </section>
        </>

    );
}

export default SignUpPage;
