package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetRequest {

    private String NickName;
    private String Comment;
    private String Image;

    public TweetRequest(String nickName, String comment, String image) {
        NickName = nickName;
        Comment = comment;
        Image = image;
    }
}
