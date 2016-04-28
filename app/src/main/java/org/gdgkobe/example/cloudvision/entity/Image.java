package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image {
    private String content;

    public Image(String content) {
        this.content = content;
    }
}