type Props = {
    size: string,
    nameOfClass: string,
    onClick: () => void
}
export default function CurrentLocation({size, nameOfClass, onClick}: Props) {
    return (
        <svg onClick={onClick} width={size} height={size} className={nameOfClass} viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
            <path fill="#3b82f6" d="M16 2A11.013 11.013 0 0 0 5 13a10.889 10.889 0 0 0 2.216 6.6s.3.395.349.452L16 30l8.439-9.953c.044-.053.345-.447.345-.447l.001-.003A10.885 10.885 0 0 0 27 13A11.013 11.013 0 0 0 16 2Zm0 15a4 4 0 1 1 4-4a4.005 4.005 0 0 1-4 4Z"></path>
            <circle cx="16" cy="13" r="4" fill="none"></circle>
        </svg>
    )
}