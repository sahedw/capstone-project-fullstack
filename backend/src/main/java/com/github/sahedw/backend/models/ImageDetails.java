package com.github.sahedw.backend.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ImageDetails {

    CategoryCSSDetails categoryCard;

    FoodSpotCSSDetails foodSpotCard;
}
