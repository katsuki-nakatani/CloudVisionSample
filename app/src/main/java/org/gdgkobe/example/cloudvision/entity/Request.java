package org.gdgkobe.example.cloudvision.entity;

import android.graphics.Bitmap;

import org.gdgkobe.example.cloudvision.util.ImageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Request {

    List<AnnotateImageRequest> requests = new ArrayList<>();

    public void addRequest(Bitmap content, Feature entity) {
        addRequest(content, Arrays.asList(entity));
    }

    public void addRequest(Bitmap content, List<Feature> featureEntities) {
        requests.add(new AnnotateImageRequest(ImageUtil.convertBase64(content), featureEntities));
    }

}
