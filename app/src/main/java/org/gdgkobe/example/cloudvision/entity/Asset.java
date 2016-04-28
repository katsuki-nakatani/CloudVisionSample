package org.gdgkobe.example.cloudvision.entity;

import android.graphics.Bitmap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Asset {

    private String fileName;
    private Bitmap bitmap;

    public Asset(int no) {
        fileName = no + ".jpg";
        bitmap = null;
    }
}
