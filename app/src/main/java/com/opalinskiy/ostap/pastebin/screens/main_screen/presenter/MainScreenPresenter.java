package com.opalinskiy.ostap.pastebin.screens.main_screen.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class MainScreenPresenter implements IMainScreen.IPresenter {
    private static MainScreenPresenter instance;
    private IMainScreen.IView view;
    private IDataInteractor model;

    private MainScreenPresenter(final IMainScreen.IView view) {
        this.view = view;
        this.model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
    }

    public static MainScreenPresenter getInstance(IMainScreen.IView view) {
        if (instance == null) {
            instance = new MainScreenPresenter(view);
        } else {
            instance.setView(view);
        }
        return instance;
    }

    @Override
    public void loadUser(final SharedPreferences prefs, final boolean userChanged) {

        String userKey = prefs.getString(Constants.USER_KEY_TAG, "");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "userdetails");

        model.getUser(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.TAG, "loadUser() onSuccess():  " + object);
                User user = (User) object;
                if (user != null) {
                    setUserInfo(user);
                }
                if (userChanged) {
                    view.setProfileScreen();
                }
                SharedPreferences.Editor ed = prefs.edit();
                ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
            }

            @Override
            public void onFailure(Object object) {
                Log.d(Constants.TAG, "onFailure: " + object);
            }
        });
    }


    @Override
    public void loadUserKey(final SharedPreferences prefs, final boolean userChanged) {
        String login = prefs.getString(Constants.LOGIN_KEY, "Grib");
        String password = prefs.getString(Constants.PASSWORD_KEY, "123456789");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_name", login);
        parameters.put("api_user_password", password);

        Log.d(Constants.TAG, "loadUserKey " + "login " + login + "password " + password);
        model.getUserKey(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                String userKey = object.toString();
                if (userKey.equals(Constants.WRONG_PASSWORD_RESPONSE)) {
                    view.onWrongLogin();
                } else {
                    Log.d(Constants.TAG, "loadUserKey() onSuccess(), userKey:" + userKey);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putString(Constants.USER_KEY_TAG, userKey);
                    ed.apply();
                    loadUser(prefs, userChanged);
                }
            }

            @Override
            public void onFailure(Object object) {
                Log.d(Constants.TAG, "loadUserKey() onFailure:" + object);
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void setUserInfo(User user) {
        view.setAvatar(user.getUserAvatarUrl());
        view.setUserName(user.getUserName());
    }

    @Override
    public void setData(SharedPreferences prefs) {
        String userKey = prefs.getString(Constants.USER_KEY_TAG, "");
        Log.d(Constants.TAG, "setData(), user key:" + userKey);
        Log.d(Constants.TAG, "setData(), user name:" + prefs.getString(Constants.LOGIN_KEY, ""));
        if(TextUtils.isEmpty(userKey)){
            loadUserKey(prefs, false);
        }else {
            loadUser(prefs, false);
        }
    }

    @Override
    public void onLogout(SharedPreferences prefs) {
        view.setLoginScreen();
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(Constants.IS_REGISTERED_KEY, true);

    }

    @Override
    public void onLogin(String login, String password, SharedPreferences prefs) {
        if (!(TextUtils.isEmpty(login) && TextUtils.isEmpty(password))) {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString(Constants.LOGIN_KEY, login);
            ed.putString(Constants.PASSWORD_KEY, password);
            ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
            ed.apply();
        }

        loadUserKey(prefs, true);
    }


    private void setView(IMainScreen.IView view) {
        this.view = view;
    }

}
