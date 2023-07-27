import {useNavigate} from "react-router-dom";

function BackButton() {

    const navigate = useNavigate();

    return (<>

            <button className={"back-button"} onClick={() => {
                navigate(-1)
            }}>
                <img width={40} src="/left-arrow.png" alt="left arrow"/>
            </button>

        </>
    );
}

export default BackButton;