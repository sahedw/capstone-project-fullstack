
type Props = {
    text: string,
    onClick: () => void
}
function SweetAlert2Option({text, onClick}: Props) {
    return (
        <>
            <br/>

        <p className={"sweetalert2-text"} onClick={onClick}>{text}</p>
            <br/>
        </>
    );
}

export default SweetAlert2Option;

