package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SafeSearchAnnotation {

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



    private LikeLihood adult;
    private LikeLihood spoof;
    private LikeLihood medical;
    private LikeLihood violence;



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
}
