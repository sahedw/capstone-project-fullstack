import toast from "react-hot-toast";

export default function executeRightToast(string: string, httpStatus: number) {
    if (string === httpStatus.toString()) {
        toast("Username already exists, sorry.", {
            icon: '🤷🏻‍',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "orangered"

            }
        })
    } else if (string.includes("Größe muss zwischen 5 und 50 sein")) {
        toast("Username needs at least 5 characters.", {
            icon: '🤷🏻‍',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "orangered"

            }
        })
    } else if (string.includes("Größe muss zwischen 8 und 50 sein")) {
        toast("Password needs at least 8 characters", {
            icon: '🤷🏻‍',
            style: {
                border: '2px solid #713200',
                padding: '10px',
                color: 'black',
                boxShadow: "8px 8px 0px -2px #000000",
                backgroundColor: "orangered"

            }
        })
    }
}
