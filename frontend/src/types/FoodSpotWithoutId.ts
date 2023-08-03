import {PriceLevel} from "./PriceLevel.ts";

export type FoodSpotWithoutId = {
    name: string,
    address: string,
    category: string,
    instagramUsername: string,
    priceLevel: PriceLevel
}
