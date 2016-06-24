package com.opalinskiy.ostap.pastebin.screens.mainScreen.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class MainScreenPresenter implements IMainScreen.IPresenter {
    private static MainScreenPresenter instance;
    private IMainScreen.IView view;
    private IDataInteractor model;
    private String login;
    private String password;
    // TODO: try to store fields below somewhere in model
    private User user;
    private String userKey;
    private boolean isRegistered;

    private MainScreenPresenter(final IMainScreen.IView view, SharedPreferences prefs) {
        this.view = view;
        this.model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        // default user for testing
        login = prefs.getString(Constants.LOGIN_KEY, "Grib");
        password = prefs.getString(Constants.PASSWORD_KEY, "123456789");
    }

    public static MainScreenPresenter getInstance(IMainScreen.IView view, SharedPreferences preferences) {
        if (instance == null) {
            instance = new MainScreenPresenter(view, preferences);
        } else {
            instance.setView(view);
        }
        return instance;
    }


   // to load the user we need to request user key first
    @Override
    public void loadUser(final SharedPreferences prefs, final boolean userChanged) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "userdetails");

        model.getUser(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.TAG, "loadUser() onSuccess():  " + object);
                user = (User) object;
                if (user != null) {
                    user.setUserKey(userKey);
                    isRegistered = true;
                    Log.d(Constants.TAG, "loadUser()  isRegistered: " + isRegistered);
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

   // retrieves user key and, if success, calls loadUser()
    @Override
    public void loadUserKey(final SharedPreferences prefs, final boolean userChanged) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_name", login);
        parameters.put("api_user_password", password);
        Log.d(Constants.TAG, "loadUserKey " + "login " + login + "password " + password);
        model.getUserKey(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                userKey = (String) object;
                if (userKey.equals(Constants.WRONG_PASSWORD_RESPONSE)) {
                    view.onWrongLogin();
                } else {
                    Log.d(Constants.TAG, "loadUserKey() onSuccess(), userKey:" + userKey);
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
    public User getUser() {
        return user;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void setUserInfo(User user) {
        view.setAvatar(user.getUserAvatarUrl());
        view.setUserName(user.getUserName());
        view.setUser(user);
    }

    @Override
    public void setData(SharedPreferences prefs) {
        if (user != null) {
            setUserInfo(user);
        } else {
            loadUserKey(prefs, false);
        }
    }

    @Override
    public void onLogout() {
        view.setLoginScreen();
        isRegistered = false;
    }

    @Override
    public void onLogin(String login, String password, SharedPreferences prefs) {
        if (!(TextUtils.isEmpty(login) && TextUtils.isEmpty(password))) {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString(Constants.LOGIN_KEY, login);
            ed.putString(Constants.PASSWORD_KEY, password);
            ed.commit();
        }
        this.login = prefs.getString(Constants.LOGIN_KEY, null);
        this.password = prefs.getString(Constants.PASSWORD_KEY, null);
        Log.d(Constants.TAG, "onLogin() " + "login " + login + "password " + password);
        loadUserKey(prefs, true);
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

    private void setView(IMainScreen.IView view) {
        this.view = view;
    }

}
