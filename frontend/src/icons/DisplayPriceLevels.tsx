
type Props = {
    size: string,
    priceLevel: string
}
export default function ChoosePriceLevels({size, priceLevel}: Props) {

    return (
        <>
            <svg width={size} height={size} viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" className={"priceLevel-low"}>
                <path  fill={priceLevel === "LOW" || priceLevel === "MIDDLE" || priceLevel === "HIGH" ? "#278664" : "#000000"} d="M8.2 6.8c-.1 0-.1-.1-.2-.1V3.6c1.2.1 2.2.6 2.2.6l.9-1.8c-.1 0-1.5-.8-3.1-.8V0H7v1.6c-.8.2-1.4.5-2 .9c-.6.6-1 1.4-1 2.3c0 .7.2 2.3 3 3.6v3.9c-.9-.2-2-.7-2.4-.9l-1 1.7c.2.1 1.8 1 3.4 1.2V16h1v-1.7c2.3-.3 3.6-2.1 3.6-3.8c0-1.5-1-2.7-3.4-3.7zM7 6.2c-.8-.5-1-1-1-1.3c0-.4.1-.7.4-.9l.6-.3v2.5zm1 6.1V8.9c1.1.5 1.6 1.1 1.6 1.6c0 .6-.3 1.6-1.6 1.8z"></path>
            </svg>
            <svg width={size} height={size} viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" className={"priceLevel-middle"}>
                <path fill={priceLevel === "MIDDLE" || priceLevel === "HIGH" ? "#278664" : "#000000"} d="M8.2 6.8c-.1 0-.1-.1-.2-.1V3.6c1.2.1 2.2.6 2.2.6l.9-1.8c-.1 0-1.5-.8-3.1-.8V0H7v1.6c-.8.2-1.4.5-2 .9c-.6.6-1 1.4-1 2.3c0 .7.2 2.3 3 3.6v3.9c-.9-.2-2-.7-2.4-.9l-1 1.7c.2.1 1.8 1 3.4 1.2V16h1v-1.7c2.3-.3 3.6-2.1 3.6-3.8c0-1.5-1-2.7-3.4-3.7zM7 6.2c-.8-.5-1-1-1-1.3c0-.4.1-.7.4-.9l.6-.3v2.5zm1 6.1V8.9c1.1.5 1.6 1.1 1.6 1.6c0 .6-.3 1.6-1.6 1.8z"></path>
            </svg>
            <svg width={size} height={size} viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" className={"priceLevel-high"}>
                <path fill={priceLevel === "HIGH" ? "#278664" : "#000000"} d="M8.2 6.8c-.1 0-.1-.1-.2-.1V3.6c1.2.1 2.2.6 2.2.6l.9-1.8c-.1 0-1.5-.8-3.1-.8V0H7v1.6c-.8.2-1.4.5-2 .9c-.6.6-1 1.4-1 2.3c0 .7.2 2.3 3 3.6v3.9c-.9-.2-2-.7-2.4-.9l-1 1.7c.2.1 1.8 1 3.4 1.2V16h1v-1.7c2.3-.3 3.6-2.1 3.6-3.8c0-1.5-1-2.7-3.4-3.7zM7 6.2c-.8-.5-1-1-1-1.3c0-.4.1-.7.4-.9l.6-.3v2.5zm1 6.1V8.9c1.1.5 1.6 1.1 1.6 1.6c0 .6-.3 1.6-1.6 1.8z"></path>
            </svg>

        </>

    )
}