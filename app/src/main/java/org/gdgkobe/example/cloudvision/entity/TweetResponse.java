package org.gdgkobe.example.cloudvision.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetResponse {

    private int ResultCode;
    private String Message;

    public boolean isSuccess() {
        return ResultCode == 0;
    }

}
