package com.opalinskiy.ostap.pastebin.screens.new_paste_screen.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.screens.new_paste_screen.INewPaste;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class NewPastePresenter implements INewPaste.IPresenter {
    private INewPaste.IView view;
    private IDataInteractor model;
    public static final Map<String, String> expirationMap = new HashMap<>();
    public static final Map<String, String> exposureMap = new HashMap<>();

    public NewPastePresenter(INewPaste.IView view) {
        this.view = view;
        this.model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        expirationMap.put("Never", "N");
        expirationMap.put("10 Minutes", "10M");
        expirationMap.put("1 Hour", "1H");
        expirationMap.put("1 Day", "1D");
        expirationMap.put("1 Week", "1W");
        expirationMap.put("1 Month", "1M");

        exposureMap.put("Public", "0");
        exposureMap.put("Unlisted", "1");
        exposureMap.put("Private", "2");
    }

    @Override
    public void onPostPaste(String pasteCode, String name, String syntax, String expiration, String exposure) {
        if (!TextUtils.isEmpty(pasteCode)) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("api_dev_key", Constants.API_DEV_KEY);
            parameters.put("api_paste_code", pasteCode);
            parameters.put("api_option", "paste");
            parameters.put("api_paste_name", name);
            parameters.put("api_paste_format", syntax);
            parameters.put("api_paste_expire_date", expirationMap.get(expiration));
            parameters.put("api_paste_private", exposureMap.get(exposure));

            model.postPaste(parameters, new OnLoadFinishedListener() {
                @Override
                public void onSuccess(Object object) {
                    view.showLink(object.toString());
                }

                @Override
                public void onFailure(Object object) {
                    Log.d(Constants.TAG, "onFailure() in onPostPaste()");
                }
            });
        } else {
            view.showMessage();
        }

    }

    @Override
    public void onClearLink() {
        view.clearLink();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void openLink(Context context, Intent intent) {
        context.startActivity(intent);
    }

}
