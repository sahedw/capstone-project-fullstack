import {FormEvent, useState} from "react";
import {Link} from "react-router-dom";
import SeePassword from "../icons/SeePassword.tsx";

type Props = {
    onLogin: (username: string, password: string) => void
}

function LoginPage({onLogin}: Props) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [showPassword, setShowPassword] = useState<boolean>(false)

    function handleShowPassword() {
        setShowPassword(!showPassword)
    }

    function handleLogin(e: FormEvent) {
        e.preventDefault()
        onLogin(username, password)
    }

    return (
        <section className={"form-add-container"}>
            <form onSubmit={handleLogin} className={"form login"}>
                <section className={"form-header-container"}>
                    <h2>Login into your account:</h2>
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
                </section>
                <section>
                    <Link to={"/sign-up"} className={"link sign-up"}>
                        <strong>You don't have an account? Signup here!</strong>
                    </Link>
                </section>
                <section className={"add-button-container"}>
                    <button className={"add-button"}>Login</button>
                </section>
            </form>
        </section>
    );
}

export default LoginPage;

