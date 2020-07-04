package com.adedom.library;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("upload-image.php")
    Call<UploadImageResponse> uploadImage(@Field("name") String name, @Field("image") String image);

}
