package com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.presenter;

import android.util.Log;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.screens.myPastesScreen.IMyPastesScreen;
import com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.IPasteCodeScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class PasteCodePresenter implements IPasteCodeScreen.IPresenter {
    private IDataInteractor model;
    private IPasteCodeScreen.IView view;

    public PasteCodePresenter(IPasteCodeScreen.IView view) {
        this.view = view;
        model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
    }

    @Override
    public void getCode(String url) {
        String pasteKey = url.substring(Constants.BASE_URL.length());

        model.getRawPasteCode(pasteKey, new OnLoadFinishedListener() {

            @Override
            public void onSuccess(Object object) {
                view.setCode(object.toString());
            }

            @Override
            public void onFailure(Object object) {
                view.setCode("Can't get code");
            }
        });
    }

    @Override
    public void deletePaste(String url, String userKey) {
        String pasteKey = url.substring(Constants.BASE_URL.length());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "delete");
        parameters.put("api_paste_key", pasteKey);

        model.deletePaste(parameters, new OnLoadFinishedListener() {

            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.TAG, "Paste deleted!!! " +  object);
                view.onDeletePaste(object + "");
            }

            @Override
            public void onFailure(Object object) {
                view.onDeletePaste("Cant delete paste. Error: " + object);
            }
        });

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
