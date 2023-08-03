import {PriceLevel} from "../types/PriceLevel.ts";

export default function getPriceLevelEnum(number: number) {
    const level = PriceLevel;
    if (number === 1) {
        return level.LOW
    } else if (number === 2) {
        return level.MIDDLE
    } else {
        return level.HIGH
    }
}