package com.headblocks.hbface;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaceMatcher {
    private static ApiInterface apiInterface;

    FaceMatcher() {
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
    }

    public JSONObject matchFaceOneToOne(File faceOne, File faceTwo) {
        final JSONObject[] jsonObject = new JSONObject[1];
        RequestBody imageOne = RequestBody.create(MediaType.parse("image/*"), faceOne);
        MultipartBody.Part partOne = MultipartBody.Part.createFormData("image_one", faceOne.getName(), imageOne);
        RequestBody imageTwo = RequestBody.create(MediaType.parse("image/*"), faceTwo);
        MultipartBody.Part partTwo = MultipartBody.Part.createFormData("image_two", faceTwo.getName(), imageTwo);
        Call<ResponseBody> call = apiInterface.requestOneToOneMatch(partOne, partTwo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    jsonObject[0] = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                try {
                    jsonObject[0].put("Error", 500);
                    jsonObject[0].put("Message", t.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return jsonObject[0];
    }
}
