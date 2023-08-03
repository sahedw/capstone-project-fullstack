import GooglePlacesAutocomplete from "react-google-places-autocomplete";
import Option, { ValueType } from 'react-select';

type Props = {
    apiKey: string;
    selectedPlace: ValueType<Option, true>;
    handlePlaceSelect: (selectedOption: ValueType<Option, true>) => void;
};

function AutocompleteInput({apiKey, selectedPlace, handlePlaceSelect}: Props) {


    return (
        <GooglePlacesAutocomplete
            apiKey={apiKey}
            selectProps={{
                value: selectedPlace,
                onChange: handlePlaceSelect,
                styles: {
                    container: (provided) => ({
                        ...provided,
                        height: "40px",
                        width: "250px",
                        border: "1px solid black",
                        boxShadow: "7px 7px 0px -2px #000000",
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
                        fontSize: "16px",

                    })
                }
            }}
        />
    );
}

export default AutocompleteInput;