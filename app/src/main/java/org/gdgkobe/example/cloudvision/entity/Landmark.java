package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Landmark {

    enum LandmarkType {
        UNKNOWN_LANDMARK,//Unknown face landmark detected. Should not be filled.
        LEFT_EYE,//Left eye.
        RIGHT_EYE,//Right eye.
        LEFT_OF_LEFT_EYEBROW,//Left of left eyebrow.
        RIGHT_OF_LEFT_EYEBROW,//Right of left eyebrow.
        LEFT_OF_RIGHT_EYEBROW,//Left of right eyebrow.
        RIGHT_OF_RIGHT_EYEBROW,//Right of right eyebrow.
        MIDPOINT_BETWEEN_EYES,//Midpoint between eyes.
        NOSE_TIP,//Nose tip.
        UPPER_LIP,//Upper lip.
        LOWER_LIP,//Lower lip.
        MOUTH_LEFT,//Mouth left.
        MOUTH_RIGHT,//Mouth right.
        MOUTH_CENTER,//Mouth center.
        NOSE_BOTTOM_RIGHT,//Nose, bottom right.
        NOSE_BOTTOM_LEFT,//Nose, bottom left.
        NOSE_BOTTOM_CENTER,//Nose, bottom center.
        LEFT_EYE_TOP_BOUNDARY,//Left eye, top boundary.
        LEFT_EYE_RIGHT_CORNER,//Left eye, right corner.
        LEFT_EYE_BOTTOM_BOUNDARY,//Left eye, bottom boundary.
        LEFT_EYE_LEFT_CORNER,//Left eye, left corner.
        RIGHT_EYE_TOP_BOUNDARY,//Right eye, top boundary.
        RIGHT_EYE_RIGHT_CORNER,//Right eye, right corner.
        RIGHT_EYE_BOTTOM_BOUNDARY,//Right eye, bottom boundary.
        RIGHT_EYE_LEFT_CORNER,//Right eye, left corner.
        LEFT_EYEBROW_UPPER_MIDPOINT,//Left eyebrow, upper midpoint.
        RIGHT_EYEBROW_UPPER_MIDPOINT,//Right eyebrow, upper midpoint.
        LEFT_EAR_TRAGION,//Left ear tragion.
        RIGHT_EAR_TRAGION,//Right ear tragion.
        LEFT_EYE_PUPIL,//Left eye pupil.
        RIGHT_EYE_PUPIL,//Right eye pupil.
        FOREHEAD_GLABELLA,//Forehead glabella.
        CHIN_GNATHION,//Chin gnathion.
        CHIN_LEFT_GONION,//Chin left gonion.
        CHIN_RIGHT_GONION,//Chin right gonion.
    }

    private LandmarkType type;

    private Position position;

    public String getTypeString() {
        switch (type) {
            case UNKNOWN_LANDMARK:
                return "UNKNOWN(不明です)";
            case LEFT_EYE:
                return "Left eye(左目)";
            case RIGHT_EYE:
                return "Right eye(右目)";
            case LEFT_OF_LEFT_EYEBROW:
                return "Left of left eyebrow.(左まゆの左端)";
            case RIGHT_OF_LEFT_EYEBROW:
                return "Right of left eyebrow.(左まゆの右端)";
            case LEFT_OF_RIGHT_EYEBROW:
                return "Left of right eyebrow.(右まゆの左端)";
            case RIGHT_OF_RIGHT_EYEBROW:
                return "Right of right eyebrow.(右まゆの右端)";
            case MIDPOINT_BETWEEN_EYES:
                return "Midpoint between eyes(目の中央)";
            case NOSE_TIP:
                return "Nose tip.(鼻)";
            case UPPER_LIP:
                return "Upper lip(上唇)";
            case LOWER_LIP:
                return "Lower lip.(下唇)";
            case MOUTH_LEFT:
                return "Mouth left.(口の左端)";
            case MOUTH_RIGHT:
                return "Mouth right.(口の右端)";
            case MOUTH_CENTER:
                return "Mouth center.(口の中央)";
            case NOSE_BOTTOM_RIGHT:
                return "Nose, bottom right.(鼻の右下)";
            case NOSE_BOTTOM_LEFT:
                return "Nose, bottom left.(鼻の左下)";
            case NOSE_BOTTOM_CENTER:
                return "Nose, bottom center.(鼻の中央下)";
            case LEFT_EYE_TOP_BOUNDARY:
                return "Left eye, top boundary(左目、上部境界).";
            case LEFT_EYE_RIGHT_CORNER:
                return "Left eye, right corner.(左目、右コーナー)";
            case LEFT_EYE_BOTTOM_BOUNDARY:
                return "Left eye, bottom boundary.(左目、下部境界)";
            case LEFT_EYE_LEFT_CORNER:
                return "Left eye, left corner.(左目、左コーナー)";
            case RIGHT_EYE_TOP_BOUNDARY:
                return "Right eye, top boundary.(右目、上部境界)";
            case RIGHT_EYE_RIGHT_CORNER:
                return "Right eye, right corner.(右目、右コーナー)";
            case RIGHT_EYE_BOTTOM_BOUNDARY:
                return "Right eye, bottom boundary.(右目、下部境界)";
            case RIGHT_EYE_LEFT_CORNER:
                return "Right eye, left corner.(右目、左コーナー)";
            case LEFT_EYEBROW_UPPER_MIDPOINT:
                return "Left eyebrow, upper midpoint.(左眉毛、上部中間点)";
            case RIGHT_EYEBROW_UPPER_MIDPOINT:
                return "Right eyebrow, upper midpoint.(右眉毛、上部中間点)";
            case LEFT_EAR_TRAGION:
                return "Left ear tragion.(左耳)";
            case RIGHT_EAR_TRAGION:
                return "Right ear tragion.(右耳)";
            case LEFT_EYE_PUPIL:
                return "Left eye pupil.(左瞳)";
            case RIGHT_EYE_PUPIL:
                return "Right eye pupil.(右瞳)";
            case FOREHEAD_GLABELLA:
                return "Forehead glabella.(額眉間)";
            case CHIN_GNATHION:
                return "Chin gnathion.（[顎]あご）";
            case CHIN_LEFT_GONION:
                return "Chin left gonion.([左顎]ひだりあご)";
            case CHIN_RIGHT_GONION:
                return "Chin right gonion.([右顎]みぎあご)";
            default:
                return "Unknown Type";
        }
    }
}
