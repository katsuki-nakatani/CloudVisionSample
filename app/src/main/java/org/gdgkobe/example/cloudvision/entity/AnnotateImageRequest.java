package org.gdgkobe.example.cloudvision.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnotateImageRequest {

    private Image image;
    private List<Feature> features = new ArrayList<>();

    public AnnotateImageRequest(String image, List<Feature> features) {
        this.image = new Image(image);
        this.features = features;
    }


}
