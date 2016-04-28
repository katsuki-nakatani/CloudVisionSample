package org.gdgkobe.example.cloudvision.listener;

import org.gdgkobe.example.cloudvision.entity.Poly;

public interface ImageUpdateListener {
    void setPoint(float x, float y);

    void setPoly(Poly boundingPoly, Poly fbBoundingPoly);
}
