package com.opalinskiy.ostap.pastebin.interactor;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Api {

        @FormUrlEncoded
        @POST("api/api_post.php")
        Call<String> postPaste(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("api/api_login.php")
        Call<String> getUserKey(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("api/api_post.php")
        Call<ResponseBody> getListOfPastes(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("api/api_raw.php")
        Call<String> getUserPasteCode(@FieldMap Map<String, String> map);


        @POST("raw/{pasteCode}")
        Call<String> getRawPasteCode(@Path("pasteCode")String pasteCode);

        @FormUrlEncoded
        @POST("api/api_post.php")
        Call<ResponseBody> getListOfTrendingPastes(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("api/api_post.php")
        Call<String> deletePaste(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("api/api_post.php")
        Call<ResponseBody> getUser(@FieldMap Map<String, String> map);
}
