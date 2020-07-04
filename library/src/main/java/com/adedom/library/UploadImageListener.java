package com.adedom.library;

import okhttp3.ResponseBody;

public interface UploadImageListener {

    void onUploadImageSuccess(ResponseBody responseBody);

}
