package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Feature {

    public static final String LABEL_DETECTION = "LABEL_DETECTION";

    private static final int MAX_RESULT = 5;

    public enum FeatureType {
        TYPE_UNSPECIFIED,    //Unspecified feature type.
        FACE_DETECTION,    //Run face detection.
        LANDMARK_DETECTION,    //Run landmark detection.
        LOGO_DETECTION,    //Run logo detection.
        LABEL_DETECTION,    //Run label detection.
        TEXT_DETECTION,    //Run OCR.
        SAFE_SEARCH_DETECTION,    //Run various computer vision models to compute image safe-search properties.
        IMAGE_PROPERTIES    //Compute a set of properties about the image (such as the image's dominant colors).
    }

    private FeatureType type;
    private int maxResults = 3;

    public Feature(FeatureType typeFormat, int maxResults) {
        type = typeFormat;
        this.maxResults = maxResults;
    }

    public Feature(FeatureType typeFormat) {
        this(typeFormat, MAX_RESULT);
    }


}
