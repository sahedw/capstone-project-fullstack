package com.github.sahedw.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryCSSDetails {

    private int leftPixel;

    private int topPixel;

    private int imageWidth;

    private String cloudinaryUrl;

}
