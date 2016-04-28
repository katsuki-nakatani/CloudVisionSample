package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextAnnotation {
    private String mid;
    private String locale;
    private String description;
    private float score;
    private Poly boundingPoly;
}
