package org.gdgkobe.example.cloudvision.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnotateResponse {
    private List<LabelAnnotation> labelAnnotations = new ArrayList<>();
    private List<FaceAnnotation> faceAnnotations = new ArrayList<>();
    private List<TextAnnotation> textAnnotations = new ArrayList<>();
    private SafeSearchAnnotation safeSearchAnnotation;


}
