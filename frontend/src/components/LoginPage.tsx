import {FormEvent, useState} from "react";

type Props = {
    onLogin: (username: string, password: string) => void
}

function LoginPage({onLogin}: Props) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    function handleLogin(e: FormEvent) {
        e.preventDefault()
        onLogin(username, password)
    }

    return (
        <section className={"form-add-container"}>
            <form onSubmit={handleLogin} className={"form"}>
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
                </section>
                <section className={"add-button-container"}>
                    <button className={"add-button"}>Login</button>
                </section>
            </form>
        </section>
    );
}

export default LoginPage;