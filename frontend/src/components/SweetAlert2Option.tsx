
type Props = {
    text: string,
    onClick: () => void
}
function SweetAlert2Option({text, onClick}: Props) {
    return (
        <>
        <p onClick={onClick}>{text}</p>
            <br/>
            <br/>
        </>
    );
}

export default SweetAlert2Option;

