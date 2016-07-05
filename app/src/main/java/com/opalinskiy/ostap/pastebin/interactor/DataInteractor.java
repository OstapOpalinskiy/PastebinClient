package com.opalinskiy.ostap.pastebin.interactor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DataInteractor implements IDataInteractor {
    private Api connection;
    private ConverterUtils converterUtils;
    private Context context;


    public DataInteractor(Retrofit retrofit, ConverterUtils converterUtils, Context context) {
        connection = retrofit.create(Api.class);
        this.converterUtils = converterUtils;
        this.context = context;
    }

    @Override
    public void postPaste(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {

            Call<String> call = connection.postPaste(parameters);
            call.enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 String url = response.body();
                                 listener.onSuccess(url);
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 listener.onFailure(t.getMessage());
                             }
                         }
            );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }

    }

    @Override
    public void getUserKey(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
            Call<String> call = connection.getUserKey(parameters);
            call.enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 String url = response.body();
                                 listener.onSuccess(url);
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 listener.onFailure(t.getMessage());
                             }
                         }
            );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }
    }

    @Override
    public void getUsersPastes(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
            Call<ResponseBody> call = connection.getListOfPastes(parameters);
            call.enqueue(new Callback<ResponseBody>() {
                             @Override
                             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                 PasteList pasteList = converterUtils.responseBodyToPasteList(response);
                                 listener.onSuccess(pasteList);
                             }

                             @Override
                             public void onFailure(Call<ResponseBody> call, Throwable t) {
                                 listener.onFailure(t.getMessage());
                             }
                         }
            );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }
    }

    @Override
    public void getRawPasteCode(String pasteUrl, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
            Call<String> call = connection.getRawPasteCode(pasteUrl);
            call.enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 String url = response.body();
                                 listener.onSuccess(url);
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 listener.onFailure(t.getMessage());
                             }
                         }
            );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }

    }

    @Override
    public void getListOfTrendingPastes(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
            Call<ResponseBody> call = connection.getListOfPastes(parameters);
            call.enqueue(new Callback<ResponseBody>() {
                             @Override
                             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                 PasteList pasteList = converterUtils.responseBodyToPasteList(response);
                                 listener.onSuccess(pasteList);
                             }

                             @Override
                             public void onFailure(Call<ResponseBody> call, Throwable t) {
                                 listener.onFailure(t.getMessage());
                             }
                         }
            );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }

    }

    @Override
    public void deletePaste(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
        Call<String> call = connection.deletePaste(parameters);
        call.enqueue(new Callback<String>() {
                         @Override
                         public void onResponse(Call<String> call, Response<String> response) {
                             listener.onSuccess(response.body());
                         }

                         @Override
                         public void onFailure(Call<String> call, Throwable t) {
                             listener.onFailure(t.getMessage());
                         }
                     }
        );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }

    }

    @Override
    public void getUser(Map<String, String> parameters, final OnLoadFinishedListener listener) {
        if (isThereInternetConnection()) {
        Call<ResponseBody> call = connection.getUser(parameters);
        call.enqueue(new Callback<ResponseBody>() {
                         @Override
                         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                             User user = converterUtils.responseBodyToUser(response);
                             listener.onSuccess(user);
                         }

                         @Override
                         public void onFailure(Call<ResponseBody> call, Throwable t) {
                             listener.onFailure(t.getMessage());
                         }
                     }
        );
        } else {
            listener.onFailure(Constants.NO_CONNECTION_MSG);
        }
    }

    private boolean isThereInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
