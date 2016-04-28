package org.gdgkobe.example.cloudvision.entity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaceAnnotation {

    public enum LikeLihood {
        //わからない
        UNKNOWN,
        //とてもそう思わない = 1
        VERY_UNLIKELY,
        //そう思わない = 2
        UNLIKELY,
        //そうかもしれない = 3
        POSSIBLE,
        //そう思う = 4
        LIKELY,
        //とてもそう思う = 5
        VERY_LIKELY
    }

    //顔全体の範囲
    private org.gdgkobe.example.cloudvision.entity.Poly boundingPoly;
    //顔の皮膚の範囲（全体のものより、綿密に肌のエリアだけの範囲）
    private org.gdgkobe.example.cloudvision.entity.Poly fdBoundingPoly;
    //顔のパーツに対する情報
    private List<org.gdgkobe.example.cloudvision.entity.Landmark> landmarks = new ArrayList<>();
    //顔の時計回り、反時計回りの回転量
    private float rollAngle;
    //顔が垂直に対して、右、もしくは左に向いている角度
    private float panAngle;
    //顔が水平に対して、上もしくは下に向いている角度
    private float tiltAngle;
    //信頼度
    private float detectionConfidence;
    //landmarksの信頼度
    private float landmarkingConfidence;
    //楽しんでる？
    private LikeLihood joyLikelihood;
    //悲しんでる？
    private LikeLihood sorrowLikelihood;
    //怒ってる？
    private LikeLihood angerLikelihood;
    //驚いてる？
    private LikeLihood surpriseLikelihood;
    //露出不足？
    private LikeLihood underExposedLikelihood;
    //ぼやけてる？
    private LikeLihood blurredLikelihood;
    //なんか被り物してる？
    private LikeLihood headwearLikelihood;

    public float getRating(LikeLihood likeLihood) {

        if (likeLihood == LikeLihood.UNKNOWN)
            return -1;
        else if (likeLihood == LikeLihood.VERY_UNLIKELY)
            return 1;
        else if (likeLihood == LikeLihood.UNLIKELY)
            return 2;
        else if (likeLihood == LikeLihood.POSSIBLE)
            return 3;
        else if (likeLihood == LikeLihood.LIKELY)
            return 4;
        else if (likeLihood == LikeLihood.VERY_LIKELY)
            return 5;
        else
            throw new IllegalArgumentException("パラメータが不正");
    }

    public String toJson() throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<FaceAnnotation> adapter = moshi.adapter(FaceAnnotation.class);
        return adapter.toJson(this);
    }
}
