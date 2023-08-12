import {Navigate, Outlet} from "react-router-dom";
import {useEffect, useState} from "react";
import {BounceLoader} from "react-spinners";

type Props = {
    user?: string
}
function ProtectedPaths(props: Props) {
    const [isLoading, setIsLoading] = useState<boolean>(true)
    useEffect(() => {
        const timeoutId = setTimeout(() => {
            setIsLoading(false);
        }, 500);

        return () => clearTimeout(timeoutId);
    }, []);

    if (isLoading) return (<section className={"fallback-loading-container"}>
        <BounceLoader color="#36d7b7" />
        <h2>Loading...</h2>
    </section>)

    const isAuthenticated = props.user !== undefined && props.user!== "anonymousUser"
    return (
        isAuthenticated ? <Outlet/> : <Navigate to={"/login"} />
    );
}

export default ProtectedPaths;
