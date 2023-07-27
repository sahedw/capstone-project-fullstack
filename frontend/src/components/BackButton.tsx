import {useNavigate} from "react-router-dom";

type Props = {
    setClass: string
}
function BackButton({setClass}: Props) {

    const navigate = useNavigate();

    return (<>
            <button className={`back-button ${setClass === "from-form" ? "from-form" : setClass}`} onClick={() => {
                navigate(-1)
            }}>
                <img width={40} src="/left-arrow.png" alt="left arrow"/>
            </button>

        </>
    );
}

export default BackButton;