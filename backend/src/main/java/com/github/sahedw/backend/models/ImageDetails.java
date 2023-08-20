package com.github.sahedw.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ImageDetails {

    private CategoryCSSDetails categoryCard;

    private FoodSpotCSSDetails foodSpotCard;
}
