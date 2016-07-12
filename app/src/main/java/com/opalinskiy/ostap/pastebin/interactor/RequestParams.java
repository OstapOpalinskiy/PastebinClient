package com.opalinskiy.ostap.pastebin.interactor;


import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.Application;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteCodeParams;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    private Map<String, String> parameters;
    private SharedPreferences preferences;
    private  Map<String, String> expirationMap;
    private  Map<String, String> exposureMap;

    public RequestParams() {
        parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);

        preferences = Application.getContext().getSharedPreferences(Constants.PREFS_NAME, 0);

        expirationMap = new HashMap<>();
        expirationMap.put("Never", "N");
        expirationMap.put("10 Minutes", "10M");
        expirationMap.put("1 Hour", "1H");
        expirationMap.put("1 Day", "1D");
        expirationMap.put("1 Week", "1W");
        expirationMap.put("1 Month", "1M");

        exposureMap = new HashMap<>();
        exposureMap.put("Public", "0");
        exposureMap.put("Unlisted", "1");
        exposureMap.put("Private", "2");
    }

    public  Map<String, String> getUserKeyParams(){
        String login = preferences.getString(Constants.LOGIN_KEY, "");
        String password = preferences.getString(Constants.PASSWORD_KEY, "");
        parameters.put("api_user_name", login);
        parameters.put("api_user_password", password);
        return parameters;
    }

    public Map<String, String> getUserParams(){
        String userKey = preferences.getString(Constants.USER_KEY_TAG, "");
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "userdetails");
        return parameters;
    }

    public Map<String, String> getTrendsParams(){
        parameters.put("api_option", "trends");
        return parameters;
    }

    public Map<String, String> getMyPastesParams(){
        String userKey = preferences.getString(Constants.USER_KEY_TAG, "");
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "list");
        return parameters;
    }

    public Map<String, String> getPasteCodeParams(PasteCodeParams params){
            parameters.put("api_paste_code", params.getPasteCode());
            parameters.put("api_option", "paste");
            parameters.put("api_paste_name", params.getName());
            parameters.put("api_paste_format", params.getSyntax());
            parameters.put("api_paste_expire_date",  expirationMap.get(params.getExpiration()));
            parameters.put("api_paste_private", exposureMap.get(params.getExposure()));
        return parameters;
    }
}
