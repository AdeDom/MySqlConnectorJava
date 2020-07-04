package com.adedom.library;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("upload-image.php")
    Call<ResponseBody> uploadImage(@Field("name") String name, @Field("image") String image);

}
