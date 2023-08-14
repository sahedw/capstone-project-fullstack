import {Navigate, Outlet} from "react-router-dom";
import {useEffect, useState} from "react";
import LoginSVG from "../animations/LoginAnimation/LoginAnimation.tsx";

type Props = {
    user?: string
}

function ProtectedPaths(props: Props) {
    const [isLoading, setIsLoading] = useState<boolean>(true)
    useEffect(() => {
        const timeoutId = setTimeout(() => {
            setIsLoading(false);
        }, 2000);

        return () => clearTimeout(timeoutId);
    }, []);

    if (isLoading) return (
        <section className={"fallback-loading-container green-background"}>
            <LoginSVG/>
        </section>)

    const isAuthenticated = props.user !== undefined && props.user !== "anonymousUser"
    return (
        isAuthenticated ? <Outlet/> : <Navigate to={"/login"}/>
    );
}

export default ProtectedPaths;
