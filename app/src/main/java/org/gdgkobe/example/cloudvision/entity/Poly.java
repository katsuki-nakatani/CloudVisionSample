package org.gdgkobe.example.cloudvision.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Poly {

    private List<Vertex> vertices = new ArrayList<>();
}
