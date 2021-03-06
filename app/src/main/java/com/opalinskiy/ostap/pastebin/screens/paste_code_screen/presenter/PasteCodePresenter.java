package com.opalinskiy.ostap.pastebin.screens.paste_code_screen.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.IPasteCodeScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class PasteCodePresenter implements IPasteCodeScreen.IPresenter {
    private IDataInteractor model;
    private IPasteCodeScreen.IView view;


    public PasteCodePresenter(IPasteCodeScreen.IView view) {
        this.view = view;
        model = DataInteractor.getInstance(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
    }

    @Override
    public void getCode(String url) {
        view.startProgress("Please wait...", "Paste code is loading.");
        String pasteKey = url.substring(Constants.BASE_URL.length());

        model.getRawPasteCode(pasteKey, new OnLoadFinishedListener<String>() {

            @Override
            public void onSuccess(String msg) {
                if(view != null){
                    view.stopProgress();
                    view.setCode(msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                if(view != null){
                    view.stopProgress();
                    view.showMessage(msg);
                }
            }
        });
    }

    @Override
    public void deletePaste(String url, SharedPreferences preferences) {
        String pasteKey = url.substring(Constants.BASE_URL.length());
        String userKey = preferences.getString(Constants.USER_KEY_TAG, "");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "delete");
        parameters.put("api_paste_key", pasteKey);

        model.deletePaste(parameters, new OnLoadFinishedListener<String>() {

            @Override
            public void onSuccess(String msg) {
                view.onDeletePaste(msg);
            }

            @Override
            public void onFailure(String msg) {
                view.showMessage(msg);
            }
        });

    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "PasteCode presenter onDestroy() view == null: " + (view == null));
        view = null;
    }
}
