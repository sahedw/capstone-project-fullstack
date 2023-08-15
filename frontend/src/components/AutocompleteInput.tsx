import GooglePlacesAutocomplete from "react-google-places-autocomplete";
import {Option} from "react-google-places-autocomplete/build/types";
import {ActionMeta, SingleValue} from "react-select";

type Props = {
    onSelectPlace: (newValue: SingleValue<Option>, actionMeta: ActionMeta<Option>) => void
    shadowPixel: string,
}
function AutocompleteInput({onSelectPlace, shadowPixel}: Props) {
    return (
        <GooglePlacesAutocomplete
            selectProps={{
                onChange: onSelectPlace,
                styles: {
                    container: (provided) => ({
                        ...provided,
                        height: "40px",
                        width: "250px",
                        border: "1px solid black",
                        boxShadow: `7px ${shadowPixel}px 0px -2px #000000`,
                        backgroundColor: "white"
                        // Add container styles here
                    }),
                    control: (provided) => ({
                        // Add control styles here
                        ...provided,
                        border: "1px black solid",
                        borderRadius: "none",

                    }),
                    input: (provided) => ({
                        ...provided,
                        color: 'black',
                        fontSize: "16px"

                    }),
                    loadingIndicator: (provided) => ({
                        ...provided,
                        display: "none"
                    })
                }
            }}
        />
    );
}

export default AutocompleteInput;