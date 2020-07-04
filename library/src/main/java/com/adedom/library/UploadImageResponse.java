package com.adedom.library;

import com.google.gson.annotations.SerializedName;

public class UploadImageResponse {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

}
