package org.gdgkobe.example.cloudvision.api;

import org.gdgkobe.example.cloudvision.entity.Request;
import org.gdgkobe.example.cloudvision.entity.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VisionApi {
    @Headers("Content-Type: application/json")
    @POST("/v1/images:annotate")
    Call<Response> post(@Query("key") String ApiKey, @Body Request request);
}
