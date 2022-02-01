package com.headblocks.hbface;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface ApiInterface {

    @Multipart
    @POST("one-to-one")
    Call<ResponseBody> requestOneToOneMatch(@Part MultipartBody.Part imageOne, @Part MultipartBody.Part imageTwo);
}
