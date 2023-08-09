import {useEffect} from "react";

type Props = {
    apiKey: string
}

function HeaderJsLibraryApi({apiKey}: Props) {
    useEffect(() => {
        // Delay loading the script by 1000ms (1 second)
        const scriptTimeout = setTimeout(() => {
            const script = document.createElement("script");
            script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&libraries=places`;
            script.async = true;
            document.head.appendChild(script);
        }, 500);

        return () => {
            clearTimeout(scriptTimeout);
        };
    }, [apiKey]);

    return <>
    </>;

}

export default HeaderJsLibraryApi;