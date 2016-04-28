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
public class Response {

    private List<AnnotateResponse> responses = new ArrayList<>();


    public static Response fromJson(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Response> adapter = moshi.adapter(Response.class);
        return adapter.fromJson(json);
    }

}
