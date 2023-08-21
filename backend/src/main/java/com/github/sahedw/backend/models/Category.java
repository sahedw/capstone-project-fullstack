package com.github.sahedw.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private String id;

    private String name;

    private ImageDetails imageCSSDetails;
}
